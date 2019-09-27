package com.hwxt.service.i;

import com.hwxt.pojo.SDalx;

import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2019/9/16-14:27
 * @since 2019/9/16-14:27
 */
public interface EnterPriseVersionEfileConvert {

    void  starConvaerAll();
    void  converBylibcode(Integer libcode);
    void starConvertEfile(SDalx dalx);
    List<SDalx> allDalx();

}
