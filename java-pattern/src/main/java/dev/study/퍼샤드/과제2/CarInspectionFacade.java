package dev.study.퍼샤드.과제2;

public class CarInspectionFacade {
    private final InspectionService inspectionService = new InspectionService();
    private final RepairService repairService = new RepairService();
    private final BillingService billingService = new BillingService();

    public void carRepair(String car) {
        inspectionService.inspect(car);
        repairService.repair(car);
        billingService.processBilling(car);
    }
}
