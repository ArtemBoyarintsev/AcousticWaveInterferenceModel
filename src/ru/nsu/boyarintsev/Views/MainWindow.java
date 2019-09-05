package ru.nsu.boyarintsev.Views;

import ru.nsu.boyarintsev.Conrollers.NoiseController;
import ru.nsu.boyarintsev.Models.NoiseModel;
import ru.nsu.boyarintsev.Models.PlacementCreatingParams;
import ru.nsu.boyarintsev.Models.SoundSource;
import ru.nsu.boyarintsev.Models.SoundSourceCreatingParams;
import ru.nsu.boyarintsev.Utils.WindowsUtils;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;


public class MainWindow extends JFrame {

    private Dimension windowStartDimension = new Dimension(1366,768);

    private NoiseController noiseController;

    private RoomZone roomZone = null;
    private LegendZone legendZone = null;

    private NoiseModel noiseModel;
    private int[] colors;

    public MainWindow(NoiseController nc, NoiseModel noiseModel, int[] colors)
    {
        super("Модель Активного Шумоподавления");
        this.colors = colors;
        this.noiseModel = noiseModel;
        this.noiseController = nc;
        setLayout(null);
        setSize(windowStartDimension);
        createParamChangeElements();
        createZones();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public boolean update()
    {
        if (roomZone != null && legendZone != null) {
            updateRoomZone();
            updateLegendZone();
            repaint();
            return true;
        }
        return false;
    }
    public double getStepOnAxes()
    {
        return roomZone.getStepOnAxes();
    }

    public double getValueChangeOnHorizontalAxe()
    {
        return roomZone.getValueChangesOnHorizontalAxe();
    }

    public double getValueChangeOnVerticalAxe()
    {
        return roomZone.getValueChangesOnVerticalAxe();
    }

    public void setColors(int[] colors)
    {
        this.colors = colors;
        roomZone.setColors(colors);
        legendZone.setColors(colors);
    }

    private void createZones()
    {
        createRoomZone();
        createLegendZone();
    }


    private Point getToolBarLocation()
    {
        return new Point(0,0);
    }

    private Dimension getToolBarSize()
    {
        return new Dimension(50,0);
    }

    private Point getRoomZoneLocation()
    {
        int x = getToolBarLocation().x;
        int y = getToolBarLocation().y + getToolBarSize().height;
        return new Point(x,y);
    }

    private Dimension getRoomZoneSize()
    {
        int width = 5 * getSize().width / 6 - getRoomZoneLocation().x;
        int height = 6 * getSize().height / 8 - getRoomZoneLocation().y - 25;
        return new Dimension(width,height);
    }

    private Point getLegendZoneLocation()
    {
        int x = getRoomZoneLocation().x + getRoomZoneSize().width + 30;
        int y = getRoomZoneLocation().y;
        return new Point(x,y);
    }

    private Dimension getLegendZoneSize()
    {
        int width = getSize().width - getLegendZoneLocation().x-15;
        int height = getRoomZoneSize().height;
        return new Dimension(width,height);
    }

    private void createLegendZone()
    {
        legendZone = new LegendZone(noiseModel,colors);
        add(legendZone);
        updateLegendZone();
    }
    private void updateLegendZone()
    {
        legendZone.setFocusable(false);
        legendZone.setLocation(getLegendZoneLocation());
        legendZone.setSize(getLegendZoneSize());
    }

    private void createRoomZone()
    {
        roomZone = new RoomZone(noiseModel,colors);
        add(roomZone);
        updateRoomZone();
        roomZone.makeImage();
    }

    private void updateRoomZone()
    {
        roomZone.setFocusable(false);
        roomZone.setLocation(getRoomZoneLocation());
        roomZone.setSize(getRoomZoneSize());
    }

    private void addElements()
    {
        add(widthRoom);
        add(widthRoomText);
        add(lengthRoom);
        add(lengthRoomText);
        add(xCoordinateOfNoise);
        add(xCoordinateOfNoiseText);
        add(yCoordinateOfNoise);
        add(yCoordinateOfNoiseText);
        add(amplitude);
        add(amplitudeText);
        add(frequency);
        add(frequencyText);
        add(startPhase);
        add(startPhaseText);
        add(indexOfSoundSource);
        add(indexOfSoundSourceText);
        add(changePlacementParams);
        add(createNewSoundSource);
        add(updateSoundSource);
        add(widthMovementSlider);
        add(lengthMovementSlider);
        add(phaseSlider);
    }

    private void setSizesOfElements()
    {
        Point p = new Point(getRoomZoneLocation().x, getRoomZoneLocation().y + getRoomZoneSize().height);

        WindowsUtils.settingSize(xCoordinateOfNoise,p);
        WindowsUtils.settingSize(xCoordinateOfNoiseText,new Point(xCoordinateOfNoise.getX() + xCoordinateOfNoise.getWidth(),xCoordinateOfNoise.getY()));
        WindowsUtils.settingSize(lengthMovementSlider,new Point(xCoordinateOfNoiseText.getX() + xCoordinateOfNoiseText.getWidth(),xCoordinateOfNoiseText.getY()));
        WindowsUtils.settingSize(yCoordinateOfNoise,new Point(xCoordinateOfNoise.getX(),xCoordinateOfNoise.getY() +  + xCoordinateOfNoise.getHeight() + 5));
        WindowsUtils.settingSize(yCoordinateOfNoiseText,new Point(yCoordinateOfNoise.getX() + yCoordinateOfNoise.getWidth(),yCoordinateOfNoise.getY()));
        WindowsUtils.settingSize(widthMovementSlider,new Point(yCoordinateOfNoiseText.getX() +yCoordinateOfNoiseText.getWidth(),yCoordinateOfNoise.getY()));
        WindowsUtils.settingSize(amplitude,new Point(lengthMovementSlider.getX() + lengthMovementSlider.getWidth() + 3,lengthMovementSlider.getY()));
        WindowsUtils.settingSize(amplitudeText,new Point(amplitude.getX() + amplitude.getWidth() + 3,amplitude.getY()));
        WindowsUtils.settingSize(frequency,new Point(amplitudeText.getX() + amplitudeText.getWidth() + 3,amplitude.getY()));
        WindowsUtils.settingSize(frequencyText,new Point(frequency.getX() + frequency.getWidth() + 3,frequency.getY()));
        WindowsUtils.settingSize(startPhase,new Point(frequencyText.getX() + frequencyText.getWidth() + 3,frequencyText.getY()));
        WindowsUtils.settingSize(startPhaseText,new Point(startPhase.getX() + startPhase.getWidth() + 3,startPhase.getY()));
        WindowsUtils.settingSize(indexOfSoundSource,new Point(startPhaseText.getX() + startPhaseText.getWidth() + 3,startPhaseText.getY()));
        WindowsUtils.settingSize(indexOfSoundSourceText,new Point(indexOfSoundSource.getX() + indexOfSoundSource.getWidth() + 3, indexOfSoundSource.getY()));
        //WindowsUtils.settingSize(lengthMovementSlider,new Point(xCoordinateOfNoise.getX(),xCoordinateOfNoise.getY() + xCoordinateOfNoise.getHeight() + 5));
        //WindowsUtils.settingSize(widthMovementSlider,new Point(lengthMovementSlider.getX() + lengthMovementSlider.getWidth(), lengthMovementSlider.getY()));
        WindowsUtils.settingSize(phaseSlider,new Point(widthMovementSlider.getX() + widthMovementSlider.getWidth(), widthMovementSlider.getY()));


        WindowsUtils.settingSize(widthRoom,new Point(yCoordinateOfNoise.getX(), yCoordinateOfNoise.getY() + yCoordinateOfNoise.getHeight() + 5));
        WindowsUtils.settingSize(widthRoomText,new Point(widthRoom.getX() + widthRoom.getWidth() + 3,widthRoom.getY()));
        WindowsUtils.settingSize(lengthRoom,new Point(widthRoomText.getX()+widthRoomText.getWidth()+3,widthRoomText.getY() ));
        WindowsUtils.settingSize(lengthRoomText,new Point(lengthRoom.getX() + lengthRoom.getWidth() + 3,lengthRoom.getY()));

        WindowsUtils.settingSize(createNewSoundSource,new Point(widthRoom.getX(),widthRoom.getY() + widthRoom.getHeight()+3));
        WindowsUtils.settingSize(updateSoundSource,new Point(createNewSoundSource.getX() + createNewSoundSource.getWidth(),createNewSoundSource.getY()));
        WindowsUtils.settingSize(changePlacementParams,new Point(updateSoundSource.getX() + updateSoundSource.getWidth(),updateSoundSource.getY()));


    }

    private void createParamChangeElements()
    {
        paramsInit();
        setSizesOfElements();
        addElements();
    }



    private void paramsInit()
    {
        buttonsInit();
        textFieldInit();
        sliderInit();
    }

    private void textFieldInit()
    {
        Double width = noiseModel.getPlacementWidth();
        Double length = noiseModel.getPlacementLength();

        widthRoomText.setText(width.toString());
        lengthRoomText.setText(length.toString());
        xCoordinateOfNoiseText.setText("2.0");
        yCoordinateOfNoiseText.setText("2.0");
        amplitudeText.setText("3.0");
        frequencyText.setText("700.0");
        startPhaseText.setText("0");

        textAreaInit();
    }

    private void sliderInit()
    {
        lengthMovementSlider.setOrientation(SwingConstants.HORIZONTAL);
        lengthMovementSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                try {
                    JSlider source = (JSlider) e.getSource();
                    int position = source.getValue();
                    if (position == 0) {
                        return;
                    }
                    SoundSourceCreatingParams soundSourceCreatingParams = new SoundSourceCreatingParams();
                    Double xCoordinate = soundSourceCreatingParams.xNoise = position * noiseController.getNoiseModel().getPlacementLength() / tickNo;
                    ViewUtils.setNumberText(xCoordinateOfNoiseText, xCoordinate);
                    int index = Integer.parseInt(indexOfSoundSourceText.getText());
                    noiseController.updateNoiseSource(index, soundSourceCreatingParams);
                }
                catch(IllegalArgumentException er)
                {
                    return; // не ругаемся!
                }
            }
        });
        lengthValue.setText(ViewUtils.numberTopsy(noiseController.getNoiseModel().getPlacementLength()));
        widthValue.setText(ViewUtils.numberTopsy(noiseController.getNoiseModel().getPlacementWidth()));
        widthMovementSlider.setOrientation(SwingConstants.HORIZONTAL);
        widthMovementSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source =(JSlider)e.getSource();
                try
                {
                    int position = source.getValue();
                    if (position == 0) {
                        return;
                    }
                    SoundSourceCreatingParams soundSourceCreatingParams = new SoundSourceCreatingParams();
                    Double yCoordinate = soundSourceCreatingParams.yNoise = position * noiseController.getNoiseModel().getPlacementWidth() / tickNo;
                    ViewUtils.setNumberText(yCoordinateOfNoiseText,yCoordinate);
                    int index = Integer.parseInt(indexOfSoundSourceText.getText());
                    noiseController.updateNoiseSource(index,soundSourceCreatingParams);
                }
                catch(NumberFormatException er)
                {
                    return; //не нужно ругаться если неправильный индекс!
                }
            }
        });

        phaseSlider.setOrientation(SwingConstants.HORIZONTAL);
        phaseSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source =(JSlider)e.getSource();
                try
                {
                    int position = source.getValue();
                    SoundSourceCreatingParams soundSourceCreatingParams = new SoundSourceCreatingParams();
                    Integer phase =  soundSourceCreatingParams.startPhase = position;
                    ViewUtils.setNumberText(startPhaseText,phase);
                    int index = Integer.parseInt(indexOfSoundSourceText.getText());
                    noiseController.updateNoiseSource(index,soundSourceCreatingParams);
                }
                catch(NumberFormatException er)
                {
                    return; // Если что некорректоное введено, скорее всего создается новый источник
                }
            }
        });
    }


    private void textAreaInit()
    {
        indexOfSoundSourceText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    int index = Integer.parseInt(indexOfSoundSourceText.getText());
                    SoundSource soundSource = noiseController.getSoundSourceByIndex(index);
                    if (soundSource != null) {
                        Double xCoordinate = soundSource.getXCoordinate();
                        Double yCoordinate = soundSource.getYCoordinate();

                        ViewUtils.setNumberText(amplitudeText, soundSource.getAmplitude());
                        ViewUtils.setNumberText(frequencyText, soundSource.getFrequency());
                        ViewUtils.setNumberText(startPhaseText, soundSource.getStartPhase());

                        ViewUtils.setNumberText(xCoordinateOfNoiseText, xCoordinate);
                        ViewUtils.setNumberText(yCoordinateOfNoiseText, yCoordinate);

                        lengthMovementSlider.setValue((int) (tickNo * xCoordinate / noiseController.getNoiseModel().getPlacementLength()));
                        widthMovementSlider.setValue((int) (tickNo * yCoordinate / noiseController.getNoiseModel().getPlacementWidth()));
                    }
                }
                catch(NumberFormatException er)
                {
                    return; // если что не хорошее введено, то просто выходим.
                }
            }
        });
    }

    private void buttonsInit()
    {
        createNewSoundSource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SoundSourceCreatingParams params = setSoundSourcesParams();
                    lengthMovementSlider.setValue( (int)(tickNo * params.xNoise / noiseController.getNoiseModel().getPlacementLength()));
                    widthMovementSlider.setValue((int)(tickNo * params.yNoise / noiseController.getNoiseModel().getPlacementWidth()));
                    noiseController.addNoiseSource(params);
                }
                catch(NumberFormatException exc)
                {
                    String v = "Неверное значение!\n";
                    JOptionPane.showMessageDialog(null,v);
                }
            }
        });
        updateSoundSource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SoundSourceCreatingParams params = setSoundSourcesParams();
                    int index = Integer.parseInt(indexOfSoundSourceText.getText());
                    if (!noiseController.updateNoiseSource(index, params)) {
                        JOptionPane.showMessageDialog(null,"Такого источника шума нет!");
                    }
                }
                catch(NumberFormatException exc)
                {
                    String v = "Неверное значение!\n";
                    JOptionPane.showMessageDialog(null,v);
                }
            }
        });
        changePlacementParams.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try
                {
                    PlacementCreatingParams params = setPlacementParams();
                    noiseController.setPlacementParams(params);
                }
                catch (NumberFormatException exc) {
                    String v = "Неверное значение!\n";
                    JOptionPane.showMessageDialog(null, v);
                }
            }
        });
    }

    private PlacementCreatingParams setPlacementParams() throws NumberFormatException
    {
        String widthRoomStr = this.widthRoomText.getText();
        String lengthRoomStr = lengthRoomText.getText();
        double widthRoom = Double.parseDouble(widthRoomStr);
        double lengthRoom = Double.parseDouble(lengthRoomStr);
        PlacementCreatingParams params = new PlacementCreatingParams();

        params.lengthRoom = lengthRoom;
        params.widthRoom = widthRoom;
        return params;
    }

    private SoundSourceCreatingParams setSoundSourcesParams() throws NumberFormatException
    {
        String xCoordinateOfNoiseStr = xCoordinateOfNoiseText.getText();
        String yCoordinateOfNoiseStr = yCoordinateOfNoiseText.getText();

        String amplitudeStr = amplitudeText.getText();
        String freqStr = frequencyText.getText();
        String startPhaseStr = startPhaseText.getText();

        double xCoordinateOfNoise = Double.parseDouble(xCoordinateOfNoiseStr);
        double yCoordinateOfNoise = Double.parseDouble(yCoordinateOfNoiseStr);

        double amplitudeVal = Double.parseDouble(amplitudeStr);
        double freqVal = Double.parseDouble(freqStr);
        int startPhase = Integer.parseInt(startPhaseStr);

        SoundSourceCreatingParams params = new SoundSourceCreatingParams();

        params.xNoise = xCoordinateOfNoise;
        params.yNoise = yCoordinateOfNoise;

        params.amplitude = amplitudeVal;
        params.frequency = freqVal;
        params.startPhase = startPhase;

        return params;
    }

    private final Double MIN_VAL = 0.0;
    private final Double MAX_VAL = 7.0;

    private final Double DEF_AMP = 3.0;
    private final Double DEF_FREQ = 700.0;

    private final JLabel widthValue = new JLabel("");
    private final JLabel lengthValue = new JLabel("");

    private final JLabel widthRoom = new JLabel("Ширина комнаты ");
    private final JTextField widthRoomText = new JTextField(MIN_VAL.toString(),5);

    private final JLabel lengthRoom = new JLabel("Длина комнаты ");
    private final JTextField lengthRoomText = new JTextField(MAX_VAL.toString(),5);

    private final  JLabel xCoordinateOfNoise = new JLabel("Источник шума X: ");
    private final  JTextField xCoordinateOfNoiseText = new JTextField(MIN_VAL.toString(),5);

    private final JLabel yCoordinateOfNoise = new JLabel(" Источник шума Y:");
    private final  JTextField yCoordinateOfNoiseText = new JTextField(MAX_VAL.toString(),5);

    private final  JLabel amplitude = new JLabel("Амплитуда шума ");
    private final  JTextField amplitudeText = new JTextField(DEF_AMP.toString(),5);

    private final  JLabel frequency = new JLabel("Частота шума ");
    private final  JTextField frequencyText = new JTextField(DEF_FREQ.toString(),5);

    private final  JLabel startPhase = new JLabel("Начальная Фаза");
    private final  JTextField startPhaseText = new JTextField(DEF_FREQ.toString(),5);

    private final  JLabel indexOfSoundSource = new JLabel("Индекс источника");
    private final  JTextField indexOfSoundSourceText = new JTextField("",5);

    private JSlider lengthMovementSlider = new JSlider(0,tickNo);
    private JSlider widthMovementSlider = new JSlider(0,tickNo);
    private JSlider phaseSlider = new JSlider(0,360);

    private static final int tickNo=999991;

    private final JButton createNewSoundSource = new JButton("Создать новый источник шума");
    private final JButton updateSoundSource = new JButton("Обновить существующий источник шума");
    private final JButton changePlacementParams = new JButton("Изменить параметры помещения");
}
