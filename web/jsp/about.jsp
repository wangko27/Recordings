<style type="text/css">
div#about h2 {
	margin:10px 0 0 0;
}
div#about p {
	margin:0;
	padding-left:15px;
}
div#subabout {
	margin:0;
	padding-left:15px;
}
div#statdiv {
	margin:3px;
	padding:2px;
	background-color:#F5EBDA;
	border:1px solid black;
}
table#stattable {
	width:65%;
}
table#stattable th, table#stattable td {
	padding:2px;
}
table#stattable th {
	text-align:left;
	margin-left:10px;
	width:22%;
}
</style>

<div id="about">

<div id="statdiv">
<table id="stattable" align="center">
	<tr>
		<th>Total Recordings:</th>
		<td>${totalRecordings}</td>
		<th>Total Unique Songs:</th>
		<td>${uniqueSongs}</td>
	</tr>
	<tr>
		<th>Total Countries:</th>
		<td>${totalCountries}</td>
		<th>Total Songs:</th>
		<td>${totalSongInstances}</td>
	</tr>
	<tr>
		<th>Total Cities:</th>
		<td>${totalCities}</td>
		<th>Total Venues:</th>
		<td>${totalVenues}</td>
	</tr>
</table>
</div>

<h2 style="margin-top:0px">Motivation</h2>
<p>
<span style="font-size:130%">For such a niche artist there is an astounding amount of recordings of Jonathan Richman.</span> <br />
This website strives to list every known Jonathan Richman recording (album, single, concert, studio bootleg, radio, etc). <br />
You can search for particular songs, albums, etc, and even leave comments on just about everything. <br />
As computer science majors at North Carolina State University we decided that this would be a fun project to sharpen our programming skills<br />
</p>

<h2>How We Did It</h2>
<p>
<a href="http://www.bobsnerdywebpage.com/">Bob's site</a> has hundreds of recordings with information, comments, and even track listings. <br />
We decided to put all of Bob's data in a database and make it search-able. <br />
A program was written that parsed the text on Bob's site and importing that into a database for quick retrieval<br />
We also added some of our own data and extras, such as the <a href="http://www.jrrecordings.com/graphs">graphs</a> section
</p>

<h2>How We <em>Really</em> Did It</h2>
<p>This site was built using a laundry list of technologies, but primarily we use Java Servlets with a JSP front-end. <br />
We use an MVC architecture and spent a lot of time meeting and designing the project before actually starting it. <br />
Below is a more comprehensive list of the tools we've been using:</p>

<div id="subabout">
<h3>Languages/Frameworks</h3>
<ul>
	<li>Java, with servlets and a JSP front-end</li>
	<li>Javascript (and heavy use of <a href="http://jquery.com/">jQuery</a>, and the <a href="http://www.jqplot.com/">jqPlot</a> library for graphs)</li>
	<li><a href="http://www.junit.org/">JUnit</a>(automated unit/integration testing)</li>
	<li><a href="http://seleniumhq.org/">Selenium</a> WebDriver (UI testing)</li>
	<li><a href="http://code.google.com/p/mockito/">Mockito </a>(mocking framework)</li>
	<li><a href="http://www.hibernate.org/">Hibernate</a> (ORM) </li>
	<li>HTML and CSS </li>		
</ul>

<h3>Tools</h3>
<ul>	
	<li><a href="http://unfuddle.com/">Unfuddle</a> with Mylyn integration (issue tracking)</li>
	<li><a href="http://mercurial.selenic.com/">Mercurial </a>hosted on <a href="https://bitbucket.org/">Bitbucket </a> (source control) and the <a href="http://www.javaforge.com/project/HGE">MercurialEclipse </a>plugin </li>	
	<li><a href="http://www.mysql.com/">MySQL </a>(database)</li>
	<li><a href="http://ant.apache.org/">Ant </a>(automated builds) </li>
	<li> <a href="hudson-ci.org">Hudson </a>(continuous integration server) hosted on Amazon EC2 </li>
	<li><a href="http://tomcat.apache.org/">Tomcat </a>(servlet container) </li>
</ul>

<h3>Some Statistics</h3>

<ul>
	<li>151 Java source files</li>
	<li>71 Java test files</li>
	<li>300 unit tests</li>
	<li>68.1% code coverage</li>
	<li>1100 commits to source control (and counting!)</li>
</ul>
</div>

<h2>Who We Are</h2>

<h3>Christopher Folger</h3>

Fun facts: 
<ul> 
	<li>Still in school (computer science and chemistry)</li>
	<li>Favorite tool: Mercurial</li>
	<li>Favorite Language: Clojure</li>
	<li>Website: <a href="http://chrisfolger.com">chrisfolger.com</a> </li>
	<li> .. and don't forget <a href="cefolger.net">my technical blog</a></li>
</ul>

<h3>Jonathan Brink</h3>
<p>
Graduated 2010. <br />
My main (but old) Jonathan Richman site: <a href="http://www.icecoldnugrape.com">icecoldnugrape.com</a> <br />
I also contribute to the <a href="http://jojofiles.blogspot.com/">Jojo blog</a> sometimes <br />
One time I told Jonathan Richman that I wrote a site about him, and he said he didn't know how to use that "mouse clicky thing"! <br />
Other favorite artists: Daniel Johnston, Feelies, Shaggs, Culture, Iggy, VU, Beach Boys
</p>

<h2>The Future</h2>
<ul> 
	<li>Message Board</li>
	<li>Tags for Recordings and Songs</li>
	<li>User accounts</li>
</ul>
</div>

<div style="clear:both"></div>
<div style="float:left; font-size:90%; margin-top:15px; font-family:'Times New Roman'">
This site went live: October 28 2011
</div>