<h1>Corbeau</h1>
<p>Corbeau is a simple post sharing and reading web application project. 
It is written in Java and uses Spring Boot as framework.
Api endpoints are declared in package com.semihbkgr.corbeau.controller
and admin uploading post api endpoints are in class com.semihbkgr.corbeau.controller.AuthorizedController </p>
<p>
Default admin user is 'user' and password is "password"
These values can be overriden by adding environment variable to docker-compose.yaml.
</p>
<p>
environment:<br>
    user: [custom user]<br>
    password: [custom password]
</p>

<h3>Software in used</h3>
<ul>
<li>Database : MySql 5.7</li>
<li>Cache(IMDG) : Redis</li>
<li>JPA : Hiberante (via Spring Data) </li>
</ul>

<h3>Steps to Run on Local Machine</h3>
<p>Execute this commands sequentially in the root dir of project.</p>
<ol>
<li> >> mvnw clean package</li>
<li> >> docker build -t corbeau .</li>
<li> >> docker-compose up</li>
</ol>

<p>
Now it is running and exposing port 8888.<br>
Click <a href="http://localhost:8888/">here</a> to go home page.
</p>