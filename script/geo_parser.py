import json

file='E:/iLIMS/data/china_2024.json'

with open(file, 'r') as jsonFile:
    jdata = json.load(jsonFile)

#'citycode': '0887', 'adcode': '533400', 'name': '迪庆藏族自治州', 'center': '99.70211,27.819149', 'level': 'city',
result=[]
root=dict({"adcode":jdata.get("adcode"),"name":jdata.get("name"),"center":jdata.get("center"),"level":jdata.get('level'),"parent":None})
if len(jdata.get('citycode'))!=0:
    root['citycode']=jdata.get('citycode')
else:
    root['citycode']=None

def parse(node,parentCode):
    adcode=node.get('adcode')
    citycode=node.get('citycode')
    node1=dict({"adcode":adcode,"name":node.get("name"),"center":node.get("center"),"level":node.get('level'),"parent":parentCode})
    if len(citycode) >0:
        node1['citycode']=citycode
    else:
        node1['citycode']=""
    result.append(node1)
    districts=node.get('districts')
    if len(districts) >0:
        for i in range(0,len(districts)):
            parse(districts[i],adcode)

