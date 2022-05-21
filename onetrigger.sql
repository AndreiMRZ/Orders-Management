use schooldb;
drop trigger if exists stockDecrementOrder;

DELIMITER //
create trigger stockDecrementOrder
after insert on orders
for each row
begin
update product
set product.stock = product.stock - new.quantity
where product.id = new.idProduct;
end; //
