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

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />


    <#if player??>
        <para>Current player:
        <ul>
            <li> ${player}<para>
        </ul>
            <#if listOfPlayers??>
            <para>Click on players' name to play against that player. </para>
                <#list listOfPlayers as listOfPlayer>
                    <form action="./" method="POST">

                        <ul> <li> <label for="selecting">Player #${listOfPlayer?index+1}: </label>
                        <input type="submit" name="opponent" value=${listOfPlayer}>
                        </ul>

                    </form>
                </#list>

            <#else>
                <para>There's currently no other players<para>
            </#if>

            <#if listOfGames??>
            <para>Click on a match to spectate. </para>
                <#list listOfGames?keys as key>

                    <form action="./spectator/game" method="GET">
                        <ul> <li> <label for="selecting">Match #${key?index+1}: </label>
                        <input type="hidden" name="gameID" value=${key}>
                        <input type="submit" value="${listOfGames[key].redPlayer.name} vs ${listOfGames[key].whitePlayer.name}">
                        </ul>
                        </form>
                </#list>
            </#if>
            <#if error??>
                <div class="ERROR">${error}</div>
            </#if>
    <#else>

        <#if numOfPlayer == "0">
            <div class="INFO">There's currently no player</div>
        <#else>
            <div class="INFO">Current number of players: ${numOfPlayer}</div>
        </#if>


    </#if>



    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
