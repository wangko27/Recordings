/*
 * dependencies (in order): 
 * 
 * jquery-1.5.2
 * votes.js 
 * 
 */
if(!Voter)
{
	alert('Warning: votes.js has not been correctly loaded since makeVote is undefined'); 
}

$(document).ready(function()
{		
	var voter = new Voter("songvotes" ); 
	voter.initializeDefaultUI(); 
}); 
