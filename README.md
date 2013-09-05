Recordings
==========

The second wave of the Recordings project

From scratch:

-- install tools
sudo apt-get install eclipse (will get ant and java stuff)

$ ant -version
Apache Ant(TM) version 1.8.2 compiled on May 18 2012

mysql --version
mysql  Ver 14.14 Distrib 5.5.32, for debian-linux-gnu (i686) using readline 6.2

Get Tomcat 6
http://tomcat.apache.org/download-60.cgi
Copy the contents of the zip (under "core") somewhere

Update local.properties

-- Setup database:
sudo apt-get install mysql-client
sudo apt-get install mysql-server
mysql -uroot -proot
create database jrrecord;
create user 'jrrecord'@'localhost' identified by 'jrsi1970';
grant all on *.* to 'jrrecord'@'localhost';

Get up-to-date data dump from remote jrrecordings.com
in databaseManage:
 ./getRemoteDataDump.sh
  and apply to local: ./updateLocalDB.sh dumps/remote/dump_20...etc
  run views: in mysql, source create_views.sql
  
-- Build and deploy:
mkdir dist
mkdir build
using build.xml in root of project:
ant deploy_lcl
ant deploy_web

-- start tomcat
in bin:
chmod 777 * 
./startup.sh
http://localhost:8080/Recordings
