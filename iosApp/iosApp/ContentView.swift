import SwiftUI
import shared

struct ContentView: View {
    let appName = Constants.init().appName
    let alarmViewModel: AlarmViewModel
    
    struct AlarmUIIdentifiable: Identifiable, Hashable {
        let name: String
        let location: LocationUI
        let distanceInMetersToAlert: Float
        let active: Bool
        let id = UUID()
    }
    class AlarmsStore: ObservableObject {
        @Published var alarms = [AlarmUIIdentifiable]()
    }
    
    @StateObject private var store: AlarmsStore = AlarmsStore()
    
    init() {
        alarmViewModel = AlarmViewModelImpl(getAlarmsUseCase: GetAlarmsUseCaseImpl())
    }
    
    func collectState() {
        let stateFlow = alarmViewModel.container.stateFlow
        let commonFlow = CommonFlow<AlarmState>.init(source: stateFlow, scope: alarmViewModel.scope)
        commonFlow.collect { alarmState in
            self.store.alarms = alarmState.alarms.map {
                AlarmUIIdentifiable(name: $0.name, location: $0.location, distanceInMetersToAlert: $0.distanceInMetersToAlert, active: $0.active)
            }
        } onCompletion: {
            
        } onException: { KotlinThrowable in
            
        }
        alarmViewModel.retrieveAlarms()
    }
    
    var body: some View {
        NavigationView {
            List {
                ForEach(store.alarms) { alarmUI in
                    HStack {
                        Text("\(String(format: "%.1f", alarmUI.distanceInMetersToAlert))m")
                        VStack {
                            Text(alarmUI.name)
                            let location = alarmUI.location
                            Text("\(location.latitude), \(location.longitude)")
                        }
                        Toggle("", isOn: Binding.constant(alarmUI.active))
                    }
                }
            }
            .navigationTitle(appName)
        }
        .onAppear() {
            collectState()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

