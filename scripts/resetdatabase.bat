mysql -uroot -e "drop database jrrecord"
mysql -uroot -e "create database jrrecord"
cd scripts
mysql -uroot -e "source useractivitytestdata.sql"
exit