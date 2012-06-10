-- this script cleans the database of stuff that we might have added during testing. 

use jrrecord; 
update recording set downvotes = '0'; 
update recording set upvotes = '0'; 
update recordingcomment set voterank = '0'; 
update songcomment set voterank = '0'; 
delete from useractivity; 
delete from recordingcomment where CHAR_LENGTH(text) = 1 OR CHAR_LENGTH(username) = 1; 
delete from songcomment where CHAR_LENGTH(text) = 1 OR CHAR_LENGTH(username) = 1; 
