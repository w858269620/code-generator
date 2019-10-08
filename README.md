
    数据库变动通知
    
        1、数据库、数据库表、数据库表字段记录
        2、数据库、数据库表、数据库表字段变更日志
        3、数据库、数据库表字段变动推送
        
        代码方式：数据库
        
    
    根据数据库表结构生成 
        dao:entity:biz=1:1:1
        
        1、repository
            dao
            entity
        2、service
            biz
            
            
    根据数据库表生成api接口及接口实现
    
        api
            request
            response
            api
        controller
            ApiImpl
        service
            service interface
            impl
            converter
            
    根据api接口生成vue