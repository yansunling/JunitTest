// 在缩略图上，点击鼠标右键，显示出按钮（预览、删除）
function showOperation(e){
    e.preventDefault();              // 组织默认事件
    my_thumbnail_img_active_remove() // 删除自身

    let img_id = this.id;            // 缩略图ID
    let img_url = this.src;          // 缩略图src
    
    // 动态创建两个按钮
    let my_div = document.createElement("div")
    my_div.id = 'my_thumbnail_img_active'
    my_div.style.display = 'inline'
    my_div.style.position = 'absolute'
    my_div.style.top = e.clientY + 'px'
    my_div.style.left = e.clientX + 'px'

    // 预览按钮
    let my_btn1 = document.createElement('button')
    my_btn1.innerText = "预览"
    my_btn1.onclick = function(){
        my_thumbnail_img_active_remove();

        const img = new Image()
        img.src = img_url
        const newWin = window.open('', '_blank')
        newWin.document.write(img.outerHTML)
        newWin.document.title = '图片预览'
        newWin.document.close()
    }

    // 删除按钮
    let my_btn2 = document.createElement('button')
    my_btn2.innerText = "删除" 
    my_btn2.onclick = function(){
        my_thumbnail_img_active_remove();
        document.getElementById(img_id).remove();
    }

    my_div.appendChild(my_btn1)
    my_div.appendChild(my_btn2)
    document.getElementById("my_thumbnail").appendChild(my_div);
}

// 删除右键缩略图弹出的两个按钮
function my_thumbnail_img_active_remove(){
    let dom_my_thumbnail_img_active = document.getElementById("my_thumbnail_img_active");
    if (dom_my_thumbnail_img_active){
        dom_my_thumbnail_img_active.remove();
    }
}

