create view VDepartment as
select PG.partyId deptId,fullName deptName FROM PartyGroup PG
inner join partyRelationship PR1 ON PG.partyId=PR1.toId
inner join Party P ON P.partyId=PR1.fromId
WHERE P.partyType='root';


create view VEmployee as
SELECT P.partyId,P.externalId employeeId,PS.name,gender,deptId,deptName,PC1.contact phone FROM Person PS 
INNER JOIN Party P ON PS.partyId=P.partyId 
inner join partyRelationship PR ON PR.fromId=PS.partyId
left join VDepartment D ON D.deptId=PR.toId 
LEFT JOIN partyContact PC1 ON PS.partyId=PS.partyId
WHERE P.partyType='PERSON' and PC1.contactType='mobile';