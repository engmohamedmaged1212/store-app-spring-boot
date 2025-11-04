ALTER TABLE cart_items
ADD CONSTRAINT uq_cart_product  -- (uq اختصار لـ Unique)
UNIQUE (cart_id, product_id);