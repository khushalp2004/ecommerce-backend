create table if not exists categories (
    id bigserial primary key,
    name varchar(100) not null,
    description varchar(255),
    created_at timestamp,
    updated_at timestamp
);

create table if not exists products (
    id bigserial primary key,
    name varchar(200) not null,
    description varchar(500),
    price numeric(12,2) not null,
    stock_quantity integer,
    category_id bigint not null references categories(id),
    created_at timestamp,
    updated_at timestamp
);

create index if not exists idx_products_category_id on products(category_id);

create table if not exists tags (
    id bigserial primary key,
    name varchar(100) not null,
    created_at timestamp,
    updated_at timestamp
);

create table if not exists product_tags (
    product_id bigint not null references products(id),
    tag_id bigint not null references tags(id),
    primary key (product_id, tag_id)
);

create index if not exists idx_product_tags_tag_id on product_tags(tag_id);

create table if not exists users (
    id bigserial primary key,
    email varchar(255) not null unique,
    password varchar(255) not null,
    full_name varchar(255) not null,
    enabled boolean default true,
    created_at timestamp,
    updated_at timestamp
);

create index if not exists idx_users_email on users(email);

create table if not exists user_roles (
    user_id bigint not null references users(id),
    role varchar(50) not null,
    primary key (user_id, role)
);

create index if not exists idx_user_roles_role on user_roles(role);

create table if not exists addresses (
    id bigserial primary key,
    user_id bigint not null references users(id),
    line1 varchar(255) not null,
    line2 varchar(255),
    city varchar(100) not null,
    state varchar(100) not null,
    postal_code varchar(30) not null,
    country varchar(100) not null,
    created_at timestamp,
    updated_at timestamp
);

create index if not exists idx_addresses_user_id on addresses(user_id);

create table if not exists carts (
    id bigserial primary key,
    user_id bigint not null unique references users(id),
    created_at timestamp,
    updated_at timestamp
);

create table if not exists cart_items (
    id bigserial primary key,
    cart_id bigint not null references carts(id),
    product_id bigint not null references products(id),
    quantity integer not null,
    created_at timestamp,
    updated_at timestamp
);

create index if not exists idx_cart_items_cart_id on cart_items(cart_id);
create index if not exists idx_cart_items_product_id on cart_items(product_id);

create table if not exists orders (
    id bigserial primary key,
    user_id bigint not null references users(id),
    status varchar(30) not null,
    total_amount numeric(12,2) not null,
    shipping_address varchar(255) not null,
    created_at timestamp,
    updated_at timestamp
);

create index if not exists idx_orders_user_id on orders(user_id);

create table if not exists order_items (
    id bigserial primary key,
    order_id bigint not null references orders(id),
    product_id bigint not null references products(id),
    quantity integer not null,
    price numeric(12,2) not null,
    created_at timestamp,
    updated_at timestamp
);

create index if not exists idx_order_items_order_id on order_items(order_id);
create index if not exists idx_order_items_product_id on order_items(product_id);

create table if not exists reviews (
    id bigserial primary key,
    product_id bigint not null references products(id),
    user_id bigint not null references users(id),
    rating integer not null,
    comment varchar(500) not null,
    created_at timestamp,
    updated_at timestamp
);

create index if not exists idx_reviews_product_id on reviews(product_id);
create index if not exists idx_reviews_user_id on reviews(user_id);
