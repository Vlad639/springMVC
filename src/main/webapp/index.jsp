<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Сделать изображение чёрно-белым</title>
    <style>
        .form {
            text-align: center;
        }
    </style>

</head>
<body>
<h2 style="text-align:center" >Выберите файл для загрузки</h2>
<div class = "form">

    <form action="${pageContext.request.contextPath}/blackWhiteImage" enctype="multipart/form-data" method="post">
        <p>Загрузите фотографию</p>
        <p><input type="file" name="image" multiple accept="image/*,image/jpeg">
            <input type="submit" value="Отправить"></p>
    </form>

</div>
</body>
</html>