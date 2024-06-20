insert into geo (`geoId`,`geoName`,`geoType`) VALUES
('chinaLand','国内市场','SALES_TERRITORY'),
('abroad','国际市场','SALES_TERRITORY'),
('chinaEast','东大区','SALES_TERRITORY'),
('chinaSouth','南大区','SALES_TERRITORY'),
('chinaNorth','北大区','SALES_TERRITORY'),
('HJL','黑吉辽','SALES_TERRITORY'),
('HBTJ','河北|天津','SALES_TERRITORY'),
('SGNQ','陕甘|宁青','SALES_TERRITORY'),
('CQXZ','重庆-西藏','SALES_TERRITORY'),
('SXNM','山西|内蒙','SALES_TERRITORY'),
('JXFJ','江西|福建','SALES_TERRITORY'),
('YNGZ','云南|贵州','SALES_TERRITORY'),
('YGQ','广东|广西|海南','SALES_TERRITORY');

insert into geoAssoc (`geoId`,`geoIdTo`,`assocType`) VALUES
('chinaLand','chinaEast','SALEREGION'),
('chinaLand','chinaSouth','SALEREGION'),
('chinaLand','chinaNorth','SALEREGION'),
('chinaEast','110000','SALEREGION'),
('chinaEast','310000','SALEREGION'),
('chinaEast','320000','SALEREGION'),
('chinaEast','330000','SALEREGION'),
('chinaEast','340000','SALEREGION'),
('chinaEast','370000','SALEREGION'),
('chinaEast','410000','SALEREGION'),
('chinaEast','420000','SALEREGION'),
('HJL','230000','SALEREGION'),
('HJL','220000','SALEREGION'),
('HJL','210000','SALEREGION'),
('HBTJ','130000','SALEREGION'),
('HBTJ','120000','SALEREGION'),
('SGNQ','610000','SALEREGION'),
('SGNQ','620000','SALEREGION'),
('SGNQ','630000','SALEREGION'),
('SGNQ','640000','SALEREGION'),
('SXNM','140000','SALEREGION'),
('SXNM','150000','SALEREGION'),

('chinaNorth','HJL','SALEREGION'),
('chinaNorth','HBTJ','SALEREGION'),
('chinaNorth','SGNQ','SALEREGION'),
('chinaNorth','SXNM','SALEREGION'),
('chinaNorth','650000','SALEREGION'),

('JXFJ','350000','SALEREGION'),
('JXFJ','360000','SALEREGION'),
('YNGZ','530000','SALEREGION'),
('YNGZ','520000','SALEREGION'),
('YGQ','440000','SALEREGION'),
('YGQ','450000','SALEREGION'),
('YGQ','460000','SALEREGION'),
('chinaSouth','JXFJ','SALEREGION'),
('chinaSouth','YNGZ','SALEREGION'),
('chinaSouth','YGQ','SALEREGION'),
('chinaSouth','430000','SALEREGION'),
('chinaSouth','510000','SALEREGION'),
('chinaSouth','500000','SALEREGION'),
('chinaSouth','540000','SALEREGION');

select P.partyId as #YXB FROM w_party P,w_partyGroup G where P.partyId=G.partyId and G.fullname='营销部' and P.partyType='DEPT'; ##获取营销部的ID

insert into w_partyRelationship(fromId,toId,typeId) VALUES
('#YXB','')





