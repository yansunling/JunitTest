package com.pdf;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;


import com.itextpdf.awt.geom.Rectangle2D.Float;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import com.itextpdf.text.pdf.security.*;

public class PdfKeywordFinder {


    /**
     * @description: 查找插入签名图片的最终位置，因为是插入签名图片，所以之前的关键字应只会出现一次
     * 这里直接使用了第一次查找到关键字的位置，并返回该关键字之后的坐标位置
     * @return: float[0]:页码，float[1]:最后一个字的x坐标，float[2]:最后一个字的y坐标
     */
    public static float[] getAddImagePositionXY(String pdfName, String keyword) throws IOException {
        float[] temp = new float[3];
        List<float[]> positions = PdfKeywordFinder.findKeywordPostions(pdfName, keyword);
//        PdfReader pdfReader = new PdfReader(pdfName);
//        Rectangle pageSize = pdfReader.getPageSize(1);
//        temp[0]=positions.get(0)[0];
//        temp[1]=positions.get(0)[1]*pageSize.getWidth();
//        temp[2]=positions.get(0)[2]*pageSize.getHeight();
        temp[0] = positions.get(0)[0];
        temp[1] = positions.get(0)[1] + (keyword.length() * positions.get(0)[3]);
        temp[2] = positions.get(0)[2] - positions.get(0)[3];

        return temp;
    }


    public static void sign(InputStream src  //需要签章的pdf文件路径
            , OutputStream dest  // 签完章的pdf文件路径
            , InputStream p12Stream, //p12 路径
                            char[] password
            , String reason  //签名的原因，显示在pdf签名属性中，随便填
            , String location, String chapterPath) //签名的地点，显示在pdf签名属性中，随便填
            throws GeneralSecurityException, IOException, DocumentException {
        //读取keystore ，获得私钥和证书链
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(p12Stream, password);
        String alias = (String)ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, password);
        Certificate[] chain = ks.getCertificateChain(alias);

        //下边的步骤都是固定的，照着写就行了，没啥要解释的
        // Creating the reader and the stamper，开始pdfreader
        PdfReader reader = new PdfReader(src);
        //目标文件输出流
        //创建签章工具PdfStamper ，最后一个boolean参数
        //false的话，pdf文件只允许被签名一次，多次签名，最后一次有效
        //true的话，pdf可以被追加签名，验签工具可以识别出每次签名之后文档是否被修改
        PdfStamper stamper = PdfStamper.createSignature(reader, dest, '\0', null, false);
        // 获取数字签章属性对象，设定数字签章的属性
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setReason(reason);
        appearance.setLocation(location);



         //设置签名的位置，页码，签名域名称，多次追加签名的时候，签名预名称不能一样
        //签名的位置，是图章相对于pdf页面的位置坐标，原点为pdf页面左下角
        //四个参数的分别是，图章左下角x，图章左下角y，图章右上角x，图章右上角y
        //长度为（左下角x 与 图章右上角x 的差值）
        //宽度为（图章左下角y 与 图章右上角y 的差值）
        appearance.setVisibleSignature(new Rectangle(445, 715, 545, 615), 1, "sig1");
        //读取图章图片，这个image是itext包的image
        Image image = Image.getInstance(chapterPath);
        appearance.setSignatureGraphic(image);

        appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
        //设置图章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);

        // 这里的itext提供了2个用于签名的接口，可以自己实现，后边着重说这个实现
        // 摘要算法
        ExternalDigest digest = new BouncyCastleDigest();
        // 签名算法
        ExternalSignature signature = new PrivateKeySignature(pk, DigestAlgorithms.SHA1, null);
        // 调用itext签名方法完成pdf签章CryptoStandard.CMS 签名方式，建议采用这种
        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
    }



















    /**
     * findKeywordPostions
     *  返回查找到关键字的首个文字的左上角坐标值
     *
     * @param pdfName
     * @param keyword
     * @return List<float [ ]> : float[0]:pageNum float[1]:x float[2]:y
     * @throws IOException
     */
    public static List<float[]> findKeywordPostions(String pdfName, String keyword) throws IOException {
        File pdfFile = new File(pdfName);
        byte[] pdfData = new byte[(int) pdfFile.length()];
        FileInputStream inputStream = new FileInputStream(pdfFile);
        //从输入流中读取pdfData.length个字节到字节数组中，返回读入缓冲区的总字节数，若到达文件末尾，则返回-1
        inputStream.read(pdfData);
        inputStream.close();

        List<float[]> result = new ArrayList<>();
        List<PdfPageContentPositions> pdfPageContentPositions = getPdfContentPostionsList(pdfData);

        for (PdfPageContentPositions pdfPageContentPosition : pdfPageContentPositions) {
            List<float[]> charPositions = findPositions(keyword, pdfPageContentPosition);
            if (charPositions == null || charPositions.size() < 1) {
                continue;
            }
            result.addAll(charPositions);
        }
        return result;
    }

    private static List<PdfPageContentPositions> getPdfContentPostionsList(byte[] pdfData) throws IOException {
        PdfReader reader = new PdfReader(pdfData);

        List<PdfPageContentPositions> result = new ArrayList<>();
        int pages = reader.getNumberOfPages();
        for (int pageNum = 1; pageNum <= pages; pageNum++) {
            float width = reader.getPageSize(pageNum).getWidth();
            float height = reader.getPageSize(pageNum).getHeight();

            PdfRenderListener pdfRenderListener = new PdfRenderListener(pageNum, width, height);

//解析pdf，定位位置
            PdfContentStreamProcessor processor = new PdfContentStreamProcessor(pdfRenderListener);
            PdfDictionary pageDic = reader.getPageN(pageNum);
            PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
            try {
                processor.processContent(ContentByteUtils.getContentBytesForPage(reader, pageNum), resourcesDic);
            } catch (IOException e) {
                reader.close();
                throw e;
            }
            String content = pdfRenderListener.getContent();
            List<CharPosition> charPositions = pdfRenderListener.getcharPositions();

            List<float[]> positionsList = new ArrayList<>();
            for (CharPosition charPosition : charPositions) {
                float[] positions = new float[]{charPosition.getPageNum(), charPosition.getX(), charPosition.getY(), charPosition.getCharWidth()};
                positionsList.add(positions);
            }

            PdfPageContentPositions pdfPageContentPositions = new PdfPageContentPositions();
            pdfPageContentPositions.setContent(content);
            pdfPageContentPositions.setPostions(positionsList);

            result.add(pdfPageContentPositions);
        }
        reader.close();
        return result;
    }


    private static List<float[]> findPositions(String keyword, PdfPageContentPositions pdfPageContentPositions) {

        List<float[]> result = new ArrayList<>();

        String content = pdfPageContentPositions.getContent();
        List<float[]> charPositions = pdfPageContentPositions.getPositions();

        for (int pos = 0; pos < content.length(); ) {
            int positionIndex = content.indexOf(keyword, pos);
            if (positionIndex == -1) {
                break;
            }
            float[] postions = charPositions.get(positionIndex);
            result.add(postions);
            pos = positionIndex + 1;
        }
        return result;
    }

    private static class PdfPageContentPositions {
        private String content;
        private List<float[]> positions;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<float[]> getPositions() {
            return positions;
        }

        public void setPostions(List<float[]> positions) {
            this.positions = positions;
        }
    }

    private static class PdfRenderListener implements RenderListener {
        private int pageNum;
        private float pageWidth;
        private float pageHeight;
        private StringBuilder contentBuilder = new StringBuilder();
        private List<CharPosition> charPositions = new ArrayList<>();

        public PdfRenderListener(int pageNum, float pageWidth, float pageHeight) {
            this.pageNum = pageNum;
            this.pageWidth = pageWidth;
            this.pageHeight = pageHeight;
        }

        @Override
        public void beginTextBlock() {

        }

        @Override
        public void renderText(TextRenderInfo textRenderInfo) {
            List<TextRenderInfo> characterRenderInfos = textRenderInfo.getCharacterRenderInfos();
            for (TextRenderInfo renderInfo : characterRenderInfos) {
                String word = renderInfo.getText();
                if (word.length() > 1) {
                    word = word.substring(word.length() - 1, word.length());
                }
                Float rectangle = renderInfo.getAscentLine().getBoundingRectange();
                float x = (float) rectangle.getMinX();
                float y = (float) rectangle.getMinY();
                float charWidth = (float) (rectangle.getMaxX() - rectangle.getMinX());
                //也可以返回坐标相对于pdf页面大小的百分比
                float xPercent = Math.round(x / pageWidth * 10000) / 10000f;
                float yPercent = Math.round((1 - y / pageHeight) * 10000) / 10000f;

                CharPosition charPosition = new CharPosition(pageNum, x, y, charWidth);
                charPositions.add(charPosition);
                contentBuilder.append(word);
            }
        }

        @Override
        public void endTextBlock() {

        }

        @Override
        public void renderImage(ImageRenderInfo renderInfo) {

        }

        public String getContent() {
            return contentBuilder.toString();
        }

        public List<CharPosition> getcharPositions() {
            return charPositions;
        }
    }

    private static class CharPosition {
        private int pageNum = 0;
        private float x = 0;
        private float y = 0;
        private float charWidth = 0;//单个文字的宽度

        public CharPosition(int pageNum, float x, float y, float charWidth) {
            this.pageNum = pageNum;
            this.x = x;
            this.y = y;
            this.charWidth = charWidth;
        }

        public int getPageNum() {
            return pageNum;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getCharWidth() {
            return charWidth;
        }

        @Override
        public String toString() {
            return "[pageNum=" + this.pageNum + ",x=" + this.x + ",y=" + this.y + "]";
        }
    }
}
