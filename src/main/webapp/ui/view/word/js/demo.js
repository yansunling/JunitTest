(function() {

		
		
	// 将 .docx 文件转换为 HTML，并显示在容器中
       /* function loadDocxPreview(url) {
            fetch(url)
                .then(response => {
                    // 确保响应成功（状态码为 200）
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    // 读取响应的流数据
                    return response.blob();
                })
                .then(blob => {
                    let file = new File([blob], 'downloadedFile.docx');
                    var options = { inWrapper: false, ignoreWidth: true, ignoreHeight: true } // https://github.com/VolodymyrBaydalka/docxjs#api
                    docx.renderAsync(file, document.getElementById("container"), null, options)
                        .then(x => console.log("docx: finished"));
                })
                .catch(error => {
                    // 处理请求失败的情况
                    console.log('Fetch error: ', error);
                });
        }
    loadDocxPreview('http://localhost//fsm/api/fsm_api/download.do?file_serial_no=crm_b2e3618a-cae1-45ad-87de-3ae6b345665b_1');*/








})();

function readWord() {
    var file = $("#file")[0].files[0];

    var options = { inWrapper: false, ignoreWidth: false, ignoreHeight: false } // https://github.com/VolodymyrBaydalka/docxjs#api
    docx.renderAsync(file, document.getElementById("container"), null, options)
        .then(x => console.log("docx: finished"));
}