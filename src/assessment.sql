CREATE TABLE Product(
	id INTEGER(10),
	name VARCHAR(255) NOT NULL,
	category VARCHAR(100) NOT NULL,
	timestamp TIME NOT NULL,
	addedBy INTEGER(10) NOT NULL,
	PRIMARY KEY (productId)
);

CREATE TABLE ProductPrice(
	productId INTEGER(10),
	currentPrice DECIMAL(10,2) NOT NULL,
	discountPercent DECIMAL(4,2) NOT NULL,
	updatedTime TIME NOT NULL,
	updatedBy INTEGER(10) NOT NULL,
	PRIMARY KEY (productId),
	FOREIGN KEY (id) REFERENCES Product(id)
);

CREATE TABLE PriceChangeLog(
	oldPrice DECIMAL(10,2) NOT NULL,
	newPrice DECIMAL(10,2) NOT NULL,
	changedTime TIME,
	changedBy BIGINT
);

SELECT name, category, price, updatedTime, updatedBy
FROM Product
INNER JOIN ProductPrice
ON Product.id = ProductPrice.productId;