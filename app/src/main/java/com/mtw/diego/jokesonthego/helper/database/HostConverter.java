package com.mtw.diego.jokesonthego.helper.database;

import android.arch.persistence.room.TypeConverter;

import com.mtw.diego.jokesonthego.helper.Host;

public class HostConverter {
    @TypeConverter
    public static Host toHost(int index) {
        if (index == Host.CHUCK_NORRIS.getIndex()) {
            return Host.CHUCK_NORRIS;
        } else if (index == Host.I_CAN_HAZ_DAD_JOKE.getIndex()) {
            return Host.I_CAN_HAZ_DAD_JOKE;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @TypeConverter
    public static int toIndex(Host host) {
        return host.getIndex();
    }
}
