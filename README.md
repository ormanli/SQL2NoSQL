SQL2NoSQL
=============

This is a experimental tool. Very Alpha
-----------------------------------------

Experimental SQL to MongoDB connector. Parses SQL queries and executes them on MongoDB.

Currently only supports queries like 

```
SELECT * FROM TABLE WHERE COLUMN=1
SELECT A.COLUMN2 FROM TABLE A WHERE A.COLUMN1=1
```


TODO List
-----------------------------------------

- [x] Support basic query
- [x] Add column support
- [x] Add table alias
- [ ] Add other data types to where condition