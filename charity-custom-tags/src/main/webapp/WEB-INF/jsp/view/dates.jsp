<%--@elvariable id="date" type="java.util.Date"--%>
<%--@elvariable id="calendar" type="java.util.Calendar"--%>
<%--@elvariable id="instant" type="java.time.Instant"--%>
<template:main htmlTitle="Displaying Dates Properly">
    <b>Date:</b>
    <nicordesigns:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" /><br />
    <b>Date, time first with separator:</b>
    <nicordesigns:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" timeFirst="true" separateDateTimeWith=" on " /><br />
    <b>Calendar:</b>
    <nicordesigns:formatDate value="${calendar}" type="both" dateStyle="full" timeStyle="full" /><br />
    <b>Instant:</b>
    <nicordesigns:formatDate value="${instant}" type="both" dateStyle="full" timeStyle="full" /><br />
    <b>Instant, time first with separator:</b>
    <nicordesigns:formatDate value="${instant}" type="both" dateStyle="full" timeStyle="full" timeFirst="true" separateDateTimeWith=" on " /><br />
</template:main>
