var main = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
    },
    save : function() {
        var formData = new FormData();
        var data = {
            newsTitle: $('#title').val(),
            newsContents: $('#content').val(),
            newsTags: [$('#tag1').val(),$('#tag2').val(),$('#tag3').val()]
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
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function(error){
          alert(JSON.stringify(error));
        })
    }
}

main.init();