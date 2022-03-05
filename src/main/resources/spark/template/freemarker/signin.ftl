<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <!-- the url changes to http://localhost:4567/(action) when method=POST-->
    <!-- the url changes to http://localhost:4567/signin?(action)=(signIn) when method=GET-->
    <!-- when the button is pressed -->

    <form action="./signin" method="POST">

      <label for="signIn">Enter username</label>
      <input name="player" /><br>
      <#if error??>
            <div class="ERROR">${error}</div>
      </#if>


      <button type="submit">Submit</button>

    </form>  
  </div>

</div>
</body>

</html>
