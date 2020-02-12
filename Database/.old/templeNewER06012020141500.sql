-- SELECT * FROM courses c 
-- LEFT JOIN transportation_temple t
-- ON c.course_transportation_temple_id = t.transportation_temple_id 
-- WHERE 1=1 AND c.course_enable = '1' 
-- GROUP BY c.course_id 
-- ORDER BY c.course_create_date

-- SELECT * FROM courses c 
-- LEFT JOIN transportation_temple t
-- ON c.course_transportation_temple_id = t.transportation_temple_id 
-- WHERE 1=1 AND c.course_enable = '1' 
-- GROUP BY c.course_id 
-- ORDER BY c.course_create_date

SELECT c.course_create_date, c.course_last_update, c.course_name, l.location_name, t.tran_name, tt.tran_time_pickup, tt.tran_time_send FROM courses c
LEFT JOIN locations l ON c.course_location_id = l.location_id
LEFT JOIN transportations t ON c.course_id = t.course_id
LEFT JOIN transportations_time tt ON t.tran_time_id = tt.tran_time_id