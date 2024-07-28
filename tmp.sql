
SELECT P.*,PG.fullName customerName,PG.shortName,pt.externalId 
FROM shipItem SI 
INNER JOIN orderShip OS ON SI.shipId=OS.id 
LEFT JOIN orderItem OI ON OS.orderNo=OI.orderNo and SI.itemId=OI.id 
LEFT JOIN product P ON OI.productCode=P.code 
LEFT JOIN `Order` O ON O.orderNo=OI.orderNo 
LEFT JOIN partyGroup PG ON PG.partyId=O.customer 
LEFT JOIN `Party` ON Party.partyId=`Order`.customer
WHERE SI.barCode=#{barCode} or SI.udi=#{barCode}",