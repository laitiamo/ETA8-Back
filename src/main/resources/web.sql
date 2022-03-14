#sql("hor_country")
SELECT
*
FROM
eta8.hor_country
#end

#sql("hor_type")
SELECT
*
FROM
eta8.hor_type
#end

#sql("hor_province")
SELECT
*
FROM
eta8.hor_province
#end

#sql("hor_city")
SELECT
*
FROM
eta8.hor_city
#end

#sql("hor_county")
SELECT
*
FROM
eta8.hor_county
#end

#sql("sys_url_role")
SELECT
*
FROM
eta8.sys_url_role
#end

#sql("sys_url")
SELECT
*
FROM
eta8.sys_url
#end

#sql("KEY_COLUMN_USAGE")
SELECT
KEY_COLUMN_USAGE.REFERENCED_TABLE_SCHEMA AS targetSchema,
KEY_COLUMN_USAGE.REFERENCED_TABLE_NAME AS targetTable,
KEY_COLUMN_USAGE.REFERENCED_COLUMN_NAME AS targetColumn
FROM
KEY_COLUMN_USAGE
WHERE
KEY_COLUMN_USAGE.CONSTRAINT_NAME LIKE '%fk%' AND
KEY_COLUMN_USAGE.TABLE_SCHEMA = 'eta8' AND
KEY_COLUMN_USAGE.TABLE_NAME = ? AND
KEY_COLUMN_USAGE.COLUMN_NAME = ?
#end

#sql("t_award")
SELECT
*
FROM
eta8.t_award
#end

#sql("t_award_type")
SELECT
*
FROM
eta8.t_award_type
#end

#sql("t_type")
SELECT
*
FROM
eta8.t_type
#end

#sql("t_candidate")
SELECT
*
FROM
eta8.t_candidate
#end

#sql("t_class")
SELECT
*
FROM
eta8.t_class
#end

#sql("t_gender")
SELECT
*
FROM
eta8.t_gender
#end

#sql("t_grade")
SELECT
*
FROM
eta8.t_grade
#end

#sql("t_major")
SELECT
*
FROM
eta8.t_major
#end

#sql("t_college")
SELECT
*
FROM
eta8.t_college
#end

#sql("t_sector")
SELECT
*
FROM
eta8.t_sector
#end

#sql("t_rank")
SELECT
*
FROM
eta8.t_rank
#end

#sql("t_paper_rank")
SELECT
*
FROM
eta8.t_paper_rank
#end

#sql("t_review")
SELECT
*
FROM
eta8.t_review
#end

#sql("t_role")
SELECT
*
FROM
eta8.t_role
#end

#sql("t_student")
SELECT
*
FROM
eta8.t_student
#end

#sql("t_teacher")
SELECT
*
FROM
eta8.t_teacher
#end

#sql("t_user")
SELECT
*
FROM
eta8.t_user
#end



#sql("t_paper_link_teacher")
SELECT
*
FROM
eta8.t_paper_link_teacher
#end

#sql("t_user_award")
SELECT
*
FROM
eta8.t_user_award
#end

#sql("t_user_paper")
SELECT
*
FROM
eta8.t_user_paper
#end

#sql("t_user_subject")
SELECT
*
FROM
eta8.t_user_subject
#end

#sql("s_type")
SELECT
*
FROM
eta8.s_type
#end

#sql("s_society")
SELECT
*
FROM
eta8.s_society
#end

#sql("s_subject_first")
SELECT
*
FROM
eta8.s_subject_first
#end

#sql("s_subject_second")
SELECT
*
FROM
eta8.s_subject_second
#end

#sql("s_subject_third")
SELECT
*
FROM
eta8.s_subject_third
#end

#sql("s_topic")
SELECT
*
FROM
eta8.s_topic
#end

#sql("s_contract")
SELECT
*
FROM
eta8.s_contract
#end

#sql("s_contract_second")
SELECT
*
FROM
eta8.s_contract_second
#end

#sql("s_contract_third")
SELECT
*
FROM
eta8.s_contract_third
#end

#sql("s_entrust")
SELECT
*
FROM
eta8.s_entrust
#end

#sql("s_first")
SELECT
*
FROM
eta8.s_first
#end

#sql("s_second")
SELECT
*
FROM
eta8.s_second
#end

#sql("s_rank")
SELECT
*
FROM
eta8.s_rank
#end

#sql("s_economic")
SELECT
*
FROM
eta8.s_economic
#end

#sql("t_level")
SELECT
*
FROM
eta8.t_level
#end

#sql("t_subject_school")
SELECT
*
FROM
eta8.t_subject_school
#end

#sql("t_subject_sponsored")
SELECT
*
FROM
eta8.t_subject_sponsored
#end

#sql("t_subject_horizon")
SELECT
*
FROM
eta8.t_subject_horizon
#end

#sql("s_source")
SELECT
*
FROM
eta8.s_source
#end

#sql("s_technical")
SELECT
*
FROM
eta8.s_technical
#end

#sql("s_property")
SELECT
*
FROM
eta8.s_property
#end

#sql("t_subject_link_teacher")
SELECT
*
FROM
eta8.t_subject_link_teacher
#end

#sql("t_subject_link_paper")
SELECT
*
FROM
eta8.t_subject_link_paper
#end

#sql("s_research_type")
SELECT
*
FROM
eta8.s_research_type
#end

#sql("s_research_area")
SELECT
*
FROM
eta8.s_research_area
#end

#sql("t_user_role")
SELECT
*
FROM
eta8.t_user_role
#end

#sql("t_instructor_link_student")
SELECT
*
FROM
eta8.t_instructor_link_student
#end

#sql("t_assistant_link_student")
SELECT
*
FROM
eta8.t_assistant_link_student
#end

#sql("t_log_review")
SELECT
*
FROM
eta8.t_log_review
#end

#sql("s_belong")
SELECT
*
FROM
eta8.s_belong
#end

#sql("s_school")
SELECT
*
FROM
eta8.s_school
#end

#sql("s_category")
SELECT
*
FROM
eta8.s_category
#end

#sql("s_category_second")
SELECT
*
FROM
eta8.s_category_second
#end

#sql("s_cooperate")
SELECT
*
FROM
eta8.s_cooperate
#end

#sql("t_log_type")
SELECT
*
FROM
eta8.t_log_type
#end

#sql("t_inform")
SELECT
*
FROM
eta8.t_inform
#end

#sql("t_picture")
SELECT
*
FROM
eta8.t_picture
#end

#sql("v_award_info")
SELECT
*
FROM
eta8.v_award_info
#end

#sql("v_subject_link_school")
SELECT
*
FROM
eta8.v_subject_link_school
#end

#sql("v_subject_link_sponsored")
SELECT
*
FROM
eta8.v_subject_link_sponsored
#end

#sql("v_subject_link_horizon")
SELECT
*
FROM
eta8.v_subject_link_horizon
#end

#sql("v_log_info")
SELECT
*
FROM
eta8.v_log_info
#end

#sql("v_teacher_subject")
SELECT
*
FROM
eta8.v_teacher_subject
#end

#sql("v_subject_link_paper")
SELECT
*
FROM
eta8.v_subject_link_paper
#end

#sql("v_student_info")
SELECT
*
FROM
eta8.v_student_info
#end

#sql("v_student_info_instructor")
SELECT
*
FROM
eta8.v_student_info_instructor
#end

#sql("v_teacher_info")
SELECT
*
FROM
eta8.v_teacher_info
#end

#sql("v_student_award")
SELECT
*
FROM
eta8.v_student_award
#end

#sql("v_teacher_award")
SELECT
*
FROM
eta8.v_teacher_award
#end

#sql("v_teacher_paper")
SELECT
*
FROM
eta8.v_teacher_paper
#end

#sql("v_student_award_instructor")
SELECT
*
FROM
eta8.v_student_award_instructor
#end

#sql("v_student_award_assistant")
SELECT
*
FROM
eta8.v_student_award_assistant
#end

#sql("v_instructor_class_info")
SELECT
*
FROM
eta8.v_instructor_class_info
#end

#sql("v_user_info")
SELECT
*
FROM
eta8.v_user_info
#end