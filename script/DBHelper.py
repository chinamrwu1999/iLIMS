import pandas as pd
from sqlalchemy import create_engine,text
from urllib.parse import quote
    
#engine=create_engine("postgresql+psycopg2://postgres:helloboy@psql:5432/hm450k")
engine = create_engine('mysql+mysqlconnector://ackcjxa:%s@oa.amswh.com:3309/miniapp' % quote('ackcjxa'))
def query(sql):
    with engine.connect() as conn:
        try:
            df=pd.read_sql(text(sql),conn)
            conn.close()
            return(df)
        except Exception as e1:
            conn.close()
            print(str(e1)[0:200])