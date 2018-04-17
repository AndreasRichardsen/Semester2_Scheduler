package db;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Workload;

public interface IFDBWorkload {
	
	public ArrayList<Workload> getAllWorkloads(boolean retrieveAssociation);
	public ArrayList<Workload> getWorkloadsForCurrentMonth(boolean retrieveAssociation);
	public ArrayList<Workload> getWorkloadsForMonth(LocalDate date, Boolean retrieveAssociation);
	public Workload findWorkloadByDate(LocalDate date, boolean retrieveAssociation);
	public int insertWorkload(Workload workload) throws Exception;
	public int updateWorkload(Workload workload);
	public int deleteWorkload(Workload workload);

}
