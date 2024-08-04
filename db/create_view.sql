
create view VDepartment as
select PG.partyId deptId,fullName deptName,PR.fromId parentId FROM PartyGroup PG
INNER JOIN Party P0 ON P0.partyId=PG.partyId
LEFT JOIN partyRelationship PR ON PR.toId=P0.partyId
LEFT JOIN Party P1 ON P1.partyId=PR.fromId
WHERE P0.partyType='DEPT' AND PR.typeId='OWN'
;
create view VEmployee as
SELECT P.partyId,P.externalId employeeId,PS.name,gender,deptId,deptName,PC1.contact phone FROM Person PS 
INNER JOIN Party P ON PS.partyId=P.partyId 
LEFT JOIN partyRelationship PR ON PR.fromId=PS.partyId
LEFT JOIN VDepartment D ON D.deptId=PR.toId 
LEFT JOIN partyContact PC1 ON (PC1.partyId=PS.partyId AND  PC1.contactType='phone') 
WHERE P.partyType='PERSON';


create view VPartner as
select PG.partyId partyId,fullName partnerName,P1.externalId partnerCode FROM PartyGroup PG
inner join partyRelationship PR1 ON PG.partyId=PR1.toId
inner join Party P ON P.partyId=PR1.fromId
inner join Party P1 ON P1.partyId=PR1.toId
WHERE P.partyType='ROOT' AND P1.partyType='COMPANY' AND PR1.typeId='partner'
UNION
SELECT P.partyId, PG.fullName partnerName,P.externalId partnerCode FROM Party P,PartyGroup PG
WHERE P.partyId=PG.partyId AND P.partyType='ROOT'



CREATE VIEW Patient AS SELECT PS.*,P.createTime FROM Person PS,Party P 
WHERE PS.partyId=P.partyId AND P.partyType='PATIENT';

CREATE VIEW BarView AS
SELECT B.barId,B.barCode,B.productCode,P.name productName,B.partnerCode,partnerName,
Pt.partyId,Pt.name,gender,age, BE.createTime receiveTime,BE.expressNo
FROM PartnerBar B 
LEFT JOIN PartyBar PB   ON B.barId=PB.barId
LEFT JOIN Product P     ON P.code = B.productCode
LEFT JOIN Patient Pt    ON Pt.partyId=PB.partyId 
LEFT JOIN VPartner VP   ON VP.partnerCode=B.partnerCode
LEFT JOIN BarExpress BE ON BE.barId=B.barId ;
