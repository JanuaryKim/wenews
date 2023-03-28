var main = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });

        $('#btn-update').on('click', function(){
            _this.update();
        })

        $('#btn-delete').on('click', function(){
            _this.delete();
        })

        $('.btn-img-delete').on('click', function(){
            _this.imgDelete();
        })

    },

    save : function() {
        var formData = new FormData();

        //name이 tags인 요소들을 가져옴
        var tags = document.getElementsByName("tags");
        const tagArr = new Array(3);
        var i = 0;
        for(tag of tags)
        {
            tagArr[i] = tag.value; //배열요소의 value들을 tagArr요소에 할당해줌
            i++;
        }
        var data = {
            newsTitle: $('#title').val(),
            newsContents: $('#content').val(),
            newsTags: tagArr
        };

        formData.append('news-dto', new Blob([ JSON.stringify(data) ], {type : "application/json"}));
        formData.append('news-images', $('#file')[0].files[0]);

        $.ajax({
            type: 'POST',
            url: '/api/auth/news',
            contentType: false,               // * 중요 *
            processData: false,               // * 중요 *
            enctype : 'multipart/form-data',  // * 중요 *
            data: formData
        }).done(function(response){

            if(response.status === 200 || response.status === 201)
            {
                alert('글이 등록되었습니다.');
                window.location.href = '/';
            }
            else
            {
                alert(response.message);
            }
        }).fail(function(error){
            alert('파일의 최대 크기를 초과 하였습니다 (10MB)');
        })
    },

    update : function () {
        var formData = new FormData();

        //name이 tags인 요소들을 가져옴
        var tags = document.getElementsByName("tags");
        const tagArr = new Array(3);
        var i = 0;
        for(tag of tags)
        {
            tagArr[i] = tag.value; //배열요소의 value들을 tagArr요소에 할당해줌
            i++;
        }
        var data = {
            newsTitle: $('#title').val(),
            newsContents: $('#content').val(),
            newsTags: tagArr
        };

        var id = $('#id').val();

        formData.append('news-dto', new Blob([ JSON.stringify(data) ], {type : "application/json"}));
        formData.append('news-images', $('#file')[0].files[0]);

        $.ajax({
            type: 'PUT',
            url: '/api/auth/news/'+id,
            contentType: false,               // * 중요 *
            processData: false,               // * 중요 *
            enctype : 'multipart/form-data',  // * 중요 *
            data: formData
        }).done(function(response) {
            if(response.status === 200 || response.status === 201)
            {
                alert('글이 등록되었습니다.');
                window.location.href = '/';
            }
            else
            {
                alert(response.message);
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/auth/news/'+id,
            dataType: 'text',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },


}

main.init();


function imgDelete (clicked_id) {
    var id = clicked_id
    $.ajax({
        type: 'DELETE',
        url: '/api/auth/newsImg/'+id,
        dataType: 'text',
        contentType:'application/json; charset=utf-8'
    }).done(function() {
        alert('이미지가 삭제되었습니다.');
        window.location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}