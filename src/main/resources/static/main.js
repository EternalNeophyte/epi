$(document).ready(() => {
    $.ajax({
        url: "http://localhost:6200/epi/cpu",
        type: "get",
        success: (cpuInfo) => {
            const cpuBlock = $("#cpu-info");
            cpuBlock.empty();
            cpuBlock.append(
                `
                <h3>Информация о системе</h3>
                <table class="table table-striped table-dark">
                  <thead>
                    <tr>
                      <th scope="col"></th>
                      <th scope="col"></th>
                      <th scope="col"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <th scope="row"></th>
                      <td>Процессор</td>
                      <td>${cpuInfo.processorName}</td>
                    </tr>
                    <tr>
                      <th scope="row"></th>
                      <td>Количество процессоров</td>
                      <td>${cpuInfo.physicalPackages}</td>
                    </tr>
                    <tr>
                      <th scope="row"></th>
                      <td>Количество физических ядер</td>
                      <td>${cpuInfo.physicalCores}</td>
                    </tr>
                    <tr>
                      <th scope="row"></th>
                      <td>Количество логических ядер</td>
                      <td>${cpuInfo.logicalCores}</td>
                    </tr>
                    <tr>
                      <th scope="row"></th>
                      <td>Микроархитектура</td>
                      <td>${cpuInfo.microarchitecture}</td>
                    </tr>
                    <tr>
                      <th scope="row"></th>
                      <td>Частота (Гц)</td>
                      <td>${cpuInfo.frequencyHz}</td>
                    </tr>
                    <tr>
                      <th scope="row"></th>
                      <td>Частота (ГГц)</td>
                      <td>${cpuInfo.frequencyGHz}</td>
                    </tr>
                  </tbody>
                </table>
                `
          );
        }
    });
});

 const submitEvalForm = () => {
    $.ajax({
        url: $("#eval-form").attr("action"),
        type: "get",
        data: $("#eval-form").serialize(),
        success: (evaluation) => {
            const evalBlock = $("#eval-info");
            evalBlock.empty();
            let row = 0;
            const html = `
                <h3>Результаты вычислений</h3>
                <p></p>
                <table class="table table-rounded table-striped table-dark">
                  <thead>
                    <tr>
                      <th scope="col"></th>
                      <td>Математическое ожидание</td>
                      <td>${evaluation.mean}</td>
                    </tr>
                    <tr>
                      <th scope="col"></th>
                      <td>Дисперсия</td>
                      <td>${evaluation.variance}</td>
                    </tr>
                    <tr>
                      <th scope="col"></th>
                      <th scope="col">Номер эксперимента</th>
                      <th scope="col">Время (мс)</th>
                    </tr>
                  </thead>
                  <tbody>` 
                    +
                    evaluation.timings.map(t => `
                    <tr>
                      <th scope="row"></th>
                      <td>${++row}</td>
                      <td>${t}</td>
                    </tr>`).join('')
                    +
                    `
                  </tbody>
                </table>`
            evalBlock.append(html);    
        } 
    });
};

