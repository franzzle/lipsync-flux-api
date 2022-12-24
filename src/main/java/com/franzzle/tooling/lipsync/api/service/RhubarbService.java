package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.service.model.RhubarbDTO;
import io.reactivex.Observable;

import java.io.IOException;

public interface RhubarbService {
    /**
     * Returns an observable that emits the progress of the lipsync process when subscribed.
     * @param rhubarbDTO that is used to look up the wav and lipsync text
     * @return an observable that emits the progress of the lipsync process when subscribed
     * @throws IOException
     */
    Observable<String> waveTolipSync(RhubarbDTO rhubarbDTO);

}
