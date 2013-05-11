<!DOCTYPE html>

<html>
<head>
    <title>Welcome to our website.</title>
</head>
<body>

    <form action="./search?query=" method="get">
        <input type="text" name="query" placeholder="Search"></input>
        <button type="submit">SEARCH</button>
    </form>

    <a href="./search?query=test">query with search provider</a>

    <form action="./payments" method="post">
        <input type="text" name="amount"></input>
        amount

        <button type="submit">BUY</button>
    </form>

</body>
</html>