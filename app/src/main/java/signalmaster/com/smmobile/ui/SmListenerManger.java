package signalmaster.com.smmobile.ui;

public class SmListenerManger {
    public interface SmChartOptionListner {
        public void onClickChartType(SmChartTypeOption option);
    }
    private SmChartOptionListner listner;
    public void setChartOptionListner(SmChartOptionListner listner) {
        this.listner = listner;
    }

    public SmListenerManger() {
        this.listner = null;
    }

    public void onChartOptionChanged(int position) {
        if (listner != null) {
            SmChartTypeOption option = SmChartTypeOption.M1;
            switch (position) {
                case 0:
                    option = SmChartTypeOption.M5;
                    break;
                case 1:
                    option = SmChartTypeOption.M15;
                    break;
                case 2:
                    option = SmChartTypeOption.M30;
                    break;
                case 3:
                    option = SmChartTypeOption.M60;
                    break;
                case 4:
                    option = SmChartTypeOption.D1;
                    break;
                case 5:
                    option = SmChartTypeOption.W1;
                    break;
                case 6:
                    option = SmChartTypeOption.MO1;
                    break;
                    default:
                        option = SmChartTypeOption.M1;
                        break;
            }
            listner.onClickChartType(option);
        }
    }
}
