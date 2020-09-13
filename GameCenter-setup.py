import os
# the --password="" should be set to the MySQL server password
os.system('mysql --user="root" --password="z*>L?yZc=3Z%b9b8R,9e4V@X" < "GameCenterSchema.sql"')
os.system('java -jar GameCenter.jar')
