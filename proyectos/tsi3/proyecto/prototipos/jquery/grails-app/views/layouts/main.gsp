<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
        <script type="text/javascript" src="${resource(dir:'js/jquery', file:'jquery-1.4.2.min.js')}" ></script>
        <link rel="stylesheet" href="http://localhost:8080/jquery/plugins/jquery-ui-1.8-SNAPSHOT/jquery-ui/themes/ui-darkness/jquery-ui-1.8.custom.css" />
        <jqui:resources theme="algo"></jqui:resources>
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <div id="grailsLogo" class="logo"><a href="http://grails.org"><img src="${resource(dir:'images',file:'grails_logo.png')}" alt="Grails" border="0" /></a></div>
        <g:layoutBody />
    </body>
</html>