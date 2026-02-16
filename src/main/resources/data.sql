insert into categories (name, description, created_at, updated_at)
values ('Electronics', 'Gadgets and devices', now(), now())
on conflict do nothing;

insert into tags (name, created_at, updated_at)
values ('audio', now(), now())
on conflict do nothing;

insert into products (name, description, price, stock_quantity, category_id, created_at, updated_at)
select 'Wireless Headphones', 'Noise cancelling', 199.99, 25, c.id, now(), now()
from categories c where c.name = 'Electronics'
on conflict do nothing;

insert into product_tags (product_id, tag_id)
select p.id, t.id
from products p
join tags t on t.name = 'audio'
where p.name = 'Wireless Headphones'
on conflict do nothing;
