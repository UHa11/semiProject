CREATE OR REPLACE TRIGGER TRG_01
AFTER INSERT ON CIRCULATION
FOR EACH ROW
DECLARE
v_expire_days CATEGORY.EXPIRATION_DATE%TYPE;
v_category_no PRODUCT.CATEGORY_NO%TYPE;
BEGIN
IF (:NEW.STATUS = 2) THEN
-- PRODUCT 테이블에서 CATEGORY_NO 조회
SELECT CATEGORY_NO
INTO v_category_no
FROM PRODUCT
WHERE PRODUCT_NO = :NEW.PRODUCT_NO;

-- CATEGORY 테이블에서 EXPIRATION_DATE(일 수) 조회
SELECT EXPIRATION_DATE
INTO v_expire_days
FROM CATEGORY
WHERE CATEGORY_NO = v_category_no;

-- INSERT INTO INVENTORY
INSERT INTO INVENTORY (
INVENTORY_NO,
STORE_NO,
PRODUCT_NO,
INVENTORY_COUNT,
EXPIRATION_DATE
) VALUES (
SEQ_INO.NEXTVAL,
:NEW.STORE_NO,
:NEW.PRODUCT_NO,
:NEW.CIRCULATION_AMOUNT,
SYSDATE + v_expire_days
);
END IF;
END;
/

CREATE OR REPLACE TRIGGER TRG_02
AFTER INSERT ON CIRCULATION
FOR EACH ROW
DECLARE
v_amount_to_reduce NUMBER := :NEW.CIRCULATION_AMOUNT;

-- INVENTORY 커서: 유통기한 빠른 순
CURSOR inv_cur IS
SELECT INVENTORY_NO, INVENTORY_COUNT
FROM INVENTORY
WHERE STORE_NO = :NEW.STORE_NO
AND PRODUCT_NO = :NEW.PRODUCT_NO
ORDER BY EXPIRATION_DATE ASC;

v_inventory_id INVENTORY.INVENTORY_NO%TYPE;
v_count        INVENTORY.INVENTORY_COUNT%TYPE;

BEGIN
IF (:NEW.STATUS = 3) THEN
FOR inv_rec IN inv_cur LOOP
EXIT WHEN v_amount_to_reduce <= 0;

v_inventory_id := inv_rec.INVENTORY_NO;
v_count := inv_rec.INVENTORY_COUNT;

IF v_count <= v_amount_to_reduce THEN
-- 전부 소진 → 삭제
DELETE FROM INVENTORY
WHERE INVENTORY_NO = v_inventory_id;

v_amount_to_reduce := v_amount_to_reduce - v_count;
ELSE
-- 일부만 소진 → 차감 후 업데이트
UPDATE INVENTORY
SET INVENTORY_COUNT = v_count - v_amount_to_reduce
WHERE INVENTORY_NO = v_inventory_id;

v_amount_to_reduce := 0;
END IF;
END LOOP;
END IF;
END;
/