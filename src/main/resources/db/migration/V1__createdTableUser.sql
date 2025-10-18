CREATE Table if not exists users
(
    id bigserial primary key,
    userName varchar(255) not null unique,
    email varchar(255) not null unique,
    profileDescription varchar(999),
    password varchar(255) not null,
    birthDate timestamp not null,
    createdAt timestamp(0) not null,
    userAvatar varchar(555),
    profileAvatar varchar(555),
    streamKey varchar(255)
);

CREATE TABLE if not exists subscriptions
(
    follower_id bigint not null references users(id) on delete cascade,
    streamer_id bigint not null references users(id) on delete cascade,
    create_at timestamp default now(),
    primary key (follower_id, streamer_id)
);

CREATE INDEX idx_subscriptions_follower ON subscriptions(follower_id);
CREATE INDEX idx_subscriptions_streamer ON subscriptions(streamer_id);