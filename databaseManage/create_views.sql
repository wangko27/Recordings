create or replace view v_album as
select si.recordingfk recordingid, 
       s.id songid, 
       s.value song, 
       r.name album
from songinstance si, recording r, lkpsong s
where si.recordingfk in (
    select r.id
    from recording r
    where r.recordingtypefk in (
        select rt.id
        from lkprecordingtype rt
        where rt.value in ('Album','Studio Bootleg')
    )
)
and si.songfk = s.id
and si.recordingfk = r.id;

create or replace view v_formattedrecordingtitles AS 
select 'recording' AS 'recording',
	   r.id AS id,
	   concat(r.year,' ',ci.value,' ',v.value) AS 'formattedTitle' 
from recording r, lkpvenue v, lkpcity ci
where r.venuefk = v.id
and r.cityfk = ci.id;

create or replace view v_notused as
select id, 'Format' category, f.value notused
from lkpformat f
where not exists (
    select 1
    from recording r
    where r.formatfk = f.id
)

union all

select id, 'Venue' category, v.value notused
from lkpvenue v
where not exists (
    select 1
    from recording r
    where r.venuefk = v.id
)

union all

select id, 'City' category, c.value notused
from lkpcity c
where not exists (
    select 1
    from recording r
    where r.cityfk = c.id
)

union all

select id, 'Country' category, co.value notused
from lkpcountry co
where not exists (
    select 1
    from recording r
    where r.countryfk = co.id
)

union all

select id, 'Media' category, m.value notused
from lkpmedia m
where not exists (
    select 1
    from recording r
    where r.mediafk = m.id
)

union all

select id, 'Quality' category, q.value notused
from lkpquality q
where not exists (
    select 1
    from recording r
    where r.qualityfk = q.id
)

union all

select id, 'RecordingType' category, rt.value notused
from lkprecordingtype rt
where not exists (
    select 1
    from recording r
    where r.recordingtypefk = rt.id
)

union all

select id, 'Song' category, s.value notused
from lkpsong s
where not exists (
    select 1
    from songinstance si
    where si.songfk = s.id
);

create or replace view v_quicksearch as
(select s.id, 'song' category, s.value, s.value formattedValue
from lkpsong s)

union all

(select r.id, 'venue' category, v.value,
    case
        when frt.formattedTitle is not null
        then frt.formattedTitle
        else v.value
    end formattedValue
 from recording r
 left join v_formattedrecordingtitles frt on frt.id = r.id, lkpvenue v
 where r.venuefk = v.id)
 
union all

(select r.id, 'city' category, c.value,
    case
        when frt.formattedTitle is not null
        then frt.formattedTitle
        else c.value
    end formattedValue
 from recording r
 left join v_formattedrecordingtitles frt on frt.id = r.id, lkpcity c
 where r.cityfk = c.id)
 
union all

(select r.id, 'country' category, c.value,
    case
        when frt.formattedTitle is not null
        then frt.formattedTitle
        else c.value
    end formattedValue
 from recording r
 left join v_formattedrecordingtitles frt on frt.id = r.id, lkpcountry c
 where r.cityfk = c.id);
CREATE OR REPLACE VIEW v_recentchanges AS

SELECT songcomment.id as referenceid,
    username as author,
    text as summary, 
    null as recordingfk,
    songfk,    
    timestamp as time,
    `lkprecentchangetype`.type as type 
FROM songcomment, `lkprecentchangetype`
WHERE `lkprecentchangetype`.type = 'songcomment'

UNION ALL

SELECT recordingcomment.id as referenceid,
    username as author,
    text as summary,
    recordingfk, 
    null as songfk, 
    timestamp as time,
    `lkprecentchangetype`.type as type 
FROM recordingcomment, `lkprecentchangetype`
WHERE `lkprecentchangetype`.type = 'recordingcomment'

UNION ALL

SELECT otherrecentchanges.id as referenceid,
    'Admin' as author, 
     summary,
     null as recordingfk,
     null as songfk, 
     timestamp as time, 
     type
FROM otherrecentchanges

UNION ALL

SELECT recording.id as referenceid, 
    'Admin' as author, 
    CONCAT('A new recording was added for ',recording.year) as summary,
    null as recordingfk,
    null as songfk,
    dateadded as time,
    type
FROM `recording`, `lkprecentchangetype`
WHERE `lkprecentchangetype`.type = 'recording'
AND dateadded IS NOT NULL 

ORDER BY time  DESC;



create or replace view v_songfrequency as 
select s.id, min(r.year) firstPlayed, max(r.year) lastPlayed, count(1) count,
    case when (count(1) < 7) then 1
         when (count(1) >= 7 and count(1) < 25) then 2
         else 3 
    end frequency
from songinstance si, recording r, lkpsong s
where r.id = si.recordingfk
and s.id = si.songfk
group by s.value;


create or replace view v_smartsong AS 
select 
       s.id as id,
       s.value as value,
       s.alias as alias,
       s.upvotes as upvotes,
       s.downvotes as downvotes,
       f.firstPlayed as firstPlayed,
       f.lastPlayed as lastPlayed,
       f.count as count,
       f.frequency as frequency,
       (select a.album
        from v_album a
        where a.songid = s.id
        limit 1
        offset 0) as album1,
       (select a.album
        from v_album a
        where a.songid = s.id
        limit 1
        offset 1) as album2,
        (select a.album
        from v_album a
        where a.songid = s.id
        limit 1
        offset 2) as album3
from lkpsong s
inner join v_songfrequency f
where s.id = f.id;/* first and last played years */
