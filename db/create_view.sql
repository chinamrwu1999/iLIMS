
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
inner join partyRelationship PR ON PR.fromId=PS.partyId
left join VDepartment D ON D.deptId=PR.toId 
LEFT JOIN partyContact PC1 ON PS.partyId=PS.partyId
WHERE P.partyType='PERSON' and PC1.contactType='mobile';


create view VPartner as
select PG.partyId partyId,fullName,P1.externalId code FROM PartyGroup PG
inner join partyRelationship PR1 ON PG.partyId=PR1.toId
inner join Party P ON P.partyId=PR1.fromId
inner join Party P1 ON P1.partyId=PR1.toId
WHERE P.partyType='ROOT' AND P1.partyType='COMPANY' AND PR1.typeId='partner';