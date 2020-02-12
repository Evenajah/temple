package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.LocationDto;

public interface LocationService {
	public List<LocationDto> getLocations();

	public LocationDto getLocation(long id);

	public LocationDto updateLocation(long id, LocationDto location);

	public LocationDto createLocation(LocationDto location);

	public Boolean deleteLocation(long id);

}
