package ${info.daoImplPackage};


import ${info.modelPackage}.${info.modelName};
import com.cang.os.common.dao.BaseMongoDaoImpl;
import ${info.daoPackage}.${info.daoName};
import org.springframework.stereotype.Repository;

/** ${info.tableComment}(${info.tableName}) **/
@Repository
public class ${info.daoName}Impl extends BaseMongoDaoImpl<${info.modelName}> implements ${info.daoName}{


	/** codegen **/
}
