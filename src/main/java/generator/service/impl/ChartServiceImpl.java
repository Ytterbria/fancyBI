package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Chart;
import generator.service.ChartService;
import generator.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【chart(图表)】的数据库操作Service实现
* @createDate 2024-12-12 14:58:21
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




