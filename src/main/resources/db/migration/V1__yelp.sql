
CREATE TABLE IF NOT EXISTS restaurants(
 id SERIAL PRIMARY KEY,
 name VARCHAR(50) NOT NULL,
 location VARCHAR(50) NOT NULL,
 price_range INT NOT NULL CHECK(price_range >=1 AND price_range <=5)

 
);

INSERT INTO restaurants (name,location,price_range) VALUES('Wendys','New York',4);
INSERT INTO restaurants (name,location,price_range) VALUES('Taco Bell','San Fran',3);
INSERT INTO restaurants (name,location,price_range) VALUES('Taco Bell','New York',4);
INSERT INTO restaurants (name,location,price_range) VALUES('Cheesecake Factory','Dallas',2);
INSERT INTO restaurants (name,location,price_range) VALUES('Cheesecake Factory','Dallas',2);
INSERT INTO restaurants (name,location,price_range) VALUES('Cheesecake Factory','Houston',2);
INSERT INTO restaurants (name,location,price_range) VALUES('Taco Bell','New York',4);



