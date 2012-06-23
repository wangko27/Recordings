#!/bin/sh

userid=jrrecord
password=jrsi1970
database=jrrecord
outputfile=dumps/local/dump_`date +"%Y-%m-%d_%T"`.sql

# execute dump command
mysqldump \
	-u$userid \
	--password=$password \
	--compact --compatible=no_table_options,mysql40 --add-drop-table \
	--ignore-table=jrrecord.v_album \
	--ignore-table=jrrecord.v_formattedrecordingtitles \
	--ignore-table=jrrecord.v_notused \
	--ignore-table=jrrecord.v_quicksearch \
	--ignore-table=jrrecord.v_recentchanges \
	--ignore-table=jrrecord.v_songfrequency \
	--ignore-table=jrrecord.v_smartsong \
	$database \
	> $outputfile

