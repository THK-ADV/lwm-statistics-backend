# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table detail_entry (
  id                            bigserial not null,
  created                       timestamptz,
  label                         varchar(255),
  entry_id                      varchar(255),
  resource_id                   bigint,
  constraint pk_detail_entry primary key (id)
);

create table resource (
  id                            bigserial not null,
  created                       timestamptz,
  label                         varchar(255),
  method                        varchar(255),
  filter                        varchar(255),
  detail                        varchar(255),
  constraint pk_resource primary key (id)
);

create table resource_entry (
  id                            bigserial not null,
  created                       timestamptz,
  label                         varchar(255),
  entry_id                      varchar(255),
  resource_id                   bigint,
  constraint pk_resource_entry primary key (id)
);

create table statistic (
  id                            bigserial not null,
  created                       timestamptz,
  method                        varchar(255),
  description                   varchar(255),
  before                        varchar(255),
  after                         varchar(255),
  constraint pk_statistic primary key (id)
);

alter table detail_entry add constraint fk_detail_entry_resource_id foreign key (resource_id) references resource (id) on delete restrict on update restrict;
create index ix_detail_entry_resource_id on detail_entry (resource_id);

alter table resource_entry add constraint fk_resource_entry_resource_id foreign key (resource_id) references resource (id) on delete restrict on update restrict;
create index ix_resource_entry_resource_id on resource_entry (resource_id);


# --- !Downs

alter table if exists detail_entry drop constraint if exists fk_detail_entry_resource_id;
drop index if exists ix_detail_entry_resource_id;

alter table if exists resource_entry drop constraint if exists fk_resource_entry_resource_id;
drop index if exists ix_resource_entry_resource_id;

drop table if exists detail_entry cascade;

drop table if exists resource cascade;

drop table if exists resource_entry cascade;

drop table if exists statistic cascade;

