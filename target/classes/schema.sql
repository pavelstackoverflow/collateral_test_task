create table CAR (
  id IDENTITY primary key,
  brand VARCHAR2(150),
  model VARCHAR2(200),
  power DOUBLE,
  year_of_issue YEAR
);

create table AIRPLANE (
  id IDENTITY primary key,
  brand VARCHAR2(150),
  model VARCHAR2(200),
  manufacturer VARCHAR2(500),
  year_of_issue YEAR,
  fuel_capacity INT,
  seats INT
);

create table CAR_ASSESSMENT (
  id IDENTITY primary key,
  assessed_value DEC(20),
  date DATE,
  car_id BIGINT,
  foreign key (car_id) references CAR(id)
);

create table AIRPLANE_ASSESSMENT (
  id IDENTITY primary key,
  assessed_value DEC(20),
  date DATE,
  airplane_id BIGINT,
  foreign key (airplane_id) references AIRPLANE(id)
);

