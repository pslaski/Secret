
<%@ page import="pl.secret.MainEvent" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'mainEvent.label', default: 'MainEvent')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'mainEvent.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'mainEvent.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="authorName" title="${message(code: 'mainEvent.authorName.label', default: 'Author Name')}" />
                        
                            <th><g:message code="mainEvent.performer.label" default="Performer" /></th>
                        
                            <g:sortableColumn property="description" title="${message(code: 'mainEvent.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="details" title="${message(code: 'mainEvent.details.label', default: 'Details')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${mainEventInstanceList}" status="i" var="mainEventInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${mainEventInstance.id}">${fieldValue(bean: mainEventInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: mainEventInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: mainEventInstance, field: "authorName")}</td>
                        
                            <td>${fieldValue(bean: mainEventInstance, field: "performer")}</td>
                        
                            <td>${fieldValue(bean: mainEventInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: mainEventInstance, field: "details")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${mainEventInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
