CREATE DEFINER=`user1`@`localhost` PROCEDURE `clone_db`(IN BASE_DB_NAME TEXT,IN NEW_DB_NAME TEXT )
BEGIN
    DECLARE done1 INT DEFAULT FALSE; 
    DECLARE tablename TEXT; 
  
    DECLARE cursortable CURSOR FOR (
        SELECT table_name
        FROM information_schema.tables
        WHERE
            table_schema=BASE_DB_NAME
        ORDER BY table_name ASC
    ); 
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;
    START TRANSACTION;
    # create db
    set @createinstance := concat("CREATE DATABASE `",NEW_DB_NAME,"`"); 
    prepare createinstance from @createinstance; 
    execute createinstance;

    OPEN cursortable; 
    read_loop: LOOP 
            FETCH cursortable INTO tablename; 
            IF done1 THEN 
                    LEAVE read_loop; 
            END IF; 
            set @createtable := concat("CREATE TABLE `",NEW_DB_NAME,"`.`",tablename,"` LIKE `",BASE_DB_NAME,"`.`",tablename,"`"); 
            prepare createtable from @createtable; 
            execute createtable;
            DEALLOCATE PREPARE createtable;
    END LOOP; 
    CLOSE cursortable;
    COMMIT;
END