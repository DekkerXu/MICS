/*
Copyright IBM Corp. 2016 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

		 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package main

import (
	"fmt"
	"strconv"
	"encoding/json"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

type  Contract struct{
    Kind1 string `json:"kind1"`//="一般意外"             
    Range1 string //= "意外身故"
    CustomQuotation1 string `json："customQuotation1"` 
    Kind2 string `json："kind2"`//= "一般意外" 
    Range2 string `json："range2"`//= "意外医疗"
    CustomQuotation2 string `json："customQuotation2"`
    Kind3 string `json："kind3"`//= "津贴及车费"
    Range3 string `json："range3"`//= "住院误工津贴"
    CustomQuotation3 string `json："customQuotation3"`
    Kind4 string `json："kind4"`//= "津贴及车费"
    Range4 string `json："range4"`//= "救护车费用报销"
    CustomQuotation4 string `json："customQuotation4"`
    Kind5 string `json："kind5`//= "交通意外"
    Range5 string `json："range5"`//= "飞机意外身故、伤残"
    CustomQuotation5 string `json："customQuotation5"`
    Kind6 string `json："kind6`//= "交通意外"
    Range6 string `json："range6"`//= "火车意外身故、伤残"
    CustomQuotation6 string `json："customQuotation6"`
    Kind7 string `json："kind7"`//= "交通意外"
    Range7 string `json："range7"`//= "船舶意外身故、伤残"
    CustomQuotation7 string `json："customQuotation7"`
    Kind8 string `json："kind8"`//= "交通意外"
    Range8 string `json："range8"`//= "汽车意外身故、伤残"
    CustomQuotation8 string `json："customQuotation8"`
    Date string `json："date"`
    TotalValue string `json："totalValue"`//?
    OrderstartDate string `json："orderstartDate"`
    OrderendDate string `json："orderendDate"`
    OrderName string `json："orderName"`
    CertifiType string `json："certifiType"`
    CertifiNumber string `json："certifiNumber"`//?
    Sex string `json："sex"`
    Birthday string `json："birthday"`
    PhoneNumber string `json："phoneNumber"`
    Email string `json："email"`
    City string `json："city"`
    OrderedName string `json："orderedName"`
    Relationship string `json："relationship"`
    CertifiType2 string `json："certifiType2"`
    CertifiNumber2 string `json："certifiNumber2"`
    Birthday2 string `json："birthday2"`
    Sex2 string `json："sex2"`
    PhoneNumber2 string `json："phoneNumber2"`
    ContractState string `json："contractState"`
    UserName string `json："userName"`
    ContractName string `json："contractName"`
    Manager string `json："manager"`
    ContractId string `json："contractId"`
}


type  Medicare struct{
    Kind string `json："kind"`
    Range_value string `json："range_value"`//range是go的关键字
    Payment string `json："payment`
    PatientName string `json："patientName"`
    CertifyType3 string `json："certifyType3"`
    CertifiNumber3 string `json："certifiNumber3"`
    Age string `json："age"`
    Sex3 string `json："sex3"`
    In_hospital string `json："in_hospital"`
    Type_value string `json："type_value"`    //type是go的关键字
    VisitTime string `json："visitTime"`
    Describe string `json："describe"`
    History string `json："history"`
    MedicalcareID string `json:"medicalcareID"`
}


type Message struct {

  Check_result string `json : "check_result"`
  Information string `json："information"`
  ContractId string `json："contractId"`
  MedicalcareID string `json:"medicalcareID"`

}

// Init initializes the chaincode state
func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### example_cc Init ###########")
	return shim.Success(nil)

}

// Invoke makes payment of X units from A to B
func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### example_cc Invoke ###########")
	function, args := stub.GetFunctionAndParameters()

	if function != "invoke" {
		return shim.Error("Unknown function call")
	}

	if len(args) < 2 {
		return shim.Error("Incorrect number of arguments. Expecting at least 2")
	}

	if args[0] == "query" {
		// queries an entity state
		return t.query(stub, args)
	}
	
	if args[0] == "check" {
		// Deletes an entity from its state
		return t.check(stub, args)
	}

	if args[0] == "savecontract" {
		// Deletes an entity from its state
		return t.savecontract(stub, args)
	}
      if args[0] == "savemedicalcare" {
		// Deletes an entity from its state
		return t.savemedicalcare(stub, args)
	}

	return shim.Error("Unknown action, check the first argument, must be one of 'query', 'check', or 'save'")
}

// Query callback representing the query of a chaincode
func (t *SimpleChaincode) query(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	fmt.Println("########### example_cc query ###########")
	var ID string // Entities
      //var contractValue,medicareValue []byte
	var err error

	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting name of the person to query")
	}

	ID = args[1]   //人名加相关数据名称

	// Get the state from the ledger
	ValueBytes, err := stub.GetState(ID)
	if err != nil {
		jsonResp := "{\"Error\":\"Failed to get state for " + ID + "\"}"
		return shim.Error(jsonResp)
	}

	if ValueBytes == nil {
		
		return shim.Success([]byte("NULL"))

	}

	jsonResp1 := "{\"Name\":\"" + ID + "\",\"Amount\":\"" + string(ValueBytes) + "\"}"
	fmt.Printf("Query Response:value1=%s", jsonResp1)
	return shim.Success([]byte(ValueBytes))
}

func (t *SimpleChaincode) check(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	fmt.Println("########### example_cc check ###########")

	var contractID,medicareID string   //人名加相关数据名称
	var err error


	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 4, function followed by 2 names and 1 value")
	}

    contractID = args[1]
    medicareID = args[2]

    // Get the state from the ledger
	// TODO: will be nice to have a GetAllState call to ledger
      var contract_Value []byte
	contract_Value, err = stub.GetState(contractID)
	if err != nil {
	return shim.Success([]byte ("NULL"))
	}
      if contract_Value==nil {
	return shim.Success([]byte ("NULL"))
        }
      var medicare_Value []byte
	medicare_Value, err = stub.GetState(medicareID)
	if err != nil {
	return shim.Success([]byte ("NULL"))
	}
      if medicare_Value==nil {
	return shim.Success([]byte ("NULL"))
        }
  fmt.Printf("contract_Value=%s,medicare_Value = %s\n",string(contract_Value),string(medicare_Value))
	contract := &Contract{}
	err = json.Unmarshal(contract_Value, &contract)
	if err != nil {
		return shim.Error("Failed to Unmarshal contractValue.json")
	}
      fmt.Println(contract)
      fmt.Println("contract.orderedName:", contract.OrderedName)
	var medicare Medicare
	err = json.Unmarshal(medicare_Value, &medicare)
	if err != nil {
		return shim.Error("Failed to Unmarshal medicareValue.json")
	}
      fmt.Println("medicare.patientName:", medicare.PatientName)
	//比较相关的数据是否符合理赔条约
  var message_value []byte
  var message Message
	if contract.OrderedName!=medicare.PatientName  {
    message.Check_result="Error"
    message.Information="姓名不匹配！"
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
	} 

	if contract.CertifiType2!= medicare.CertifyType3  {
		message.Check_result="Error"
    message.Information="证件类型不匹配！"
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
	}

   if contract.CertifiNumber2!=medicare.CertifiNumber3  {
    message.Check_result="Error"
    message.Information="证件号码不匹配！"
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
	}

   if contract.Sex2!=medicare.Sex3  {
		message.Check_result="Error"
    message.Information="性别不匹配！"
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
	}
//string时间数据转换为int时间数据
  var orderstartDate,orderendDate,visitTime int
	orderstartDate, _ = strconv.Atoi(contract.OrderstartDate)
	orderendDate, _ = strconv.Atoi(contract.OrderendDate)
	visitTime, _ = strconv.Atoi(medicare.VisitTime)
 fmt.Printf("contract.OrderstartDate=%d,contract.OrderendDate = %d,medicare.VisitTime=%d\n",contract.OrderstartDate,contract.OrderendDate,medicare.VisitTime)
   
 fmt.Printf("orderstartDate=%d,orderendDate = %d,visitTime=%d\n",orderstartDate,orderendDate,visitTime)

if orderstartDate>=visitTime {
    message.Check_result="Error"
    message.Information="合同未生效！"
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
}

if orderendDate<=visitTime{
    message.Check_result="Error"
    message.Information="合同已过期！"
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
}


  switch {
      case contract.Kind1==medicare.Kind :
         fmt.Println("符合%s类型的投保条件", contract.Kind1)     
      case contract.Kind2==medicare.Kind :
         fmt.Printf("符合%s类型的投保条件", contract.Kind2)     
      case contract.Kind3==medicare.Kind :
         fmt.Printf("符合%s类型的投保条件", contract.Kind3)     
      case contract.Kind4==medicare.Kind :
         fmt.Printf("符合%s类型的投保条件", contract.Kind4)
      case contract.Kind5==medicare.Kind :
         fmt.Printf("符合%s类型的投保条件", contract.Kind5)
      case contract.Kind6==medicare.Kind :
         fmt.Printf("符合%s类型的投保条件", contract.Kind6)
      case contract.Kind7==medicare.Kind :
         fmt.Printf("符合%s类型的投保条件", contract.Kind7)
      case contract.Kind8==medicare.Kind :
         fmt.Printf("符合%s类型的投保条件", contract.Kind8)
      default:
	   message.Check_result="Error"
     message.Information="未投保！"
     message.ContractId=contractID
     message.MedicalcareID=medicareID
     message_value,err = json.Marshal(&message)
     if err != nil {
     return shim.Error("Failed to marshal message.json")
   }else{
    return shim.Success([]byte (message_value))
    }
   }

   switch {
      case contract.Range1==medicare.Range_value :
         fmt.Println("符合%s类型的投保条件", contract.Range1)     
      case contract.Range2==medicare.Range_value :
         fmt.Printf("符合%s类型的投保条件", contract.Range2)     
      case contract.Range3==medicare.Range_value :
         fmt.Printf("符合%s类型的投保条件", contract.Range3)     
      case contract.Range4==medicare.Range_value :
         fmt.Printf("符合%s类型的投保条件", contract.Range4)
      case contract.Range5==medicare.Range_value :
         fmt.Printf("符合%s类型的投保条件", contract.Range5)
      case contract.Range6==medicare.Range_value :
         fmt.Printf("符合%s类型的投保条件", contract.Range6)
      case contract.Range7==medicare.Range_value :
         fmt.Printf("符合%s类型的投保条件", contract.Range7)
      case contract.Range8==medicare.Range_value :
         fmt.Printf("符合%s类型的投保条件", contract.Range8)
      default:
	  message.Check_result="Error"
    message.Information="未投保！"
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
   }
  total, err := strconv.ParseFloat(contract.TotalValue, 32)
  payment, err := strconv.ParseFloat(medicare.Payment, 32)
  total=total*20
  if(payment<=total){
         fmt.Printf("符合类型的投保条件")
  }else{
    message.Check_result="Success"
    message.Information=strconv.FormatFloat(total,'f',-1,32)
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
  }

    message.Check_result="Success"
    message.Information=strconv.FormatFloat(payment,'f',-1,32)
    message.ContractId=contractID
    message.MedicalcareID=medicareID
    message_value,err = json.Marshal(&message)
    fmt.Printf("message_value = %s\n",string(message_value))
    if err != nil {
    return shim.Error("Failed to marshal message.json")
  }else{
   return shim.Success([]byte (message_value))
    }
}

func (t *SimpleChaincode) savecontract(stub shim.ChaincodeStubInterface,args []string) pb.Response {

fmt.Println("########### example_cc savecontract ###########")
	var ID string   //人名加相关数据名称
	var Value  string
      var value []byte
	var err error
     if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 4")
	}

    ID = args[1]
    Value = args[2]
    contract := &Contract{}
    err = json.Unmarshal([]byte(Value), &contract)
	if err != nil {
		return shim.Error("Failed to Unmarshal contractValue.json")
	}
     contract.ContractId=ID;
     value, err = json.Marshal(&contract)
     if err != nil {
		return shim.Error("Failed to marshal contractValue.json")
	}
	// Write the state to the ledger
      err = stub.PutState(ID, value)
     fmt.Printf("ID=%s,Value = %s\n",ID,string(value))
	if err != nil {
		return shim.Error(err.Error())
	}

	if transientMap, err := stub.GetTransient(); err == nil {
		if transientData, ok := transientMap["result"]; ok {
			return shim.Success(transientData)
		}
	}

	return shim.Success(nil)
	
}

func (t *SimpleChaincode) savemedicalcare(stub shim.ChaincodeStubInterface,args []string) pb.Response {

fmt.Println("########### example_cc savemedicalcare ###########")
	var ID string   //人名加相关数据名称
	var Value  string
  var value []byte
	var err error
     if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 4")
	}

    ID = args[1]
    Value = args[2]
    var medicare Medicare
	err = json.Unmarshal([]byte(Value), &medicare)
	if err != nil {
		return shim.Error("Failed to Unmarshal medicareValue.json")
	}
     medicare.MedicalcareID=ID;
     value, err = json.Marshal(&medicare)
     if err != nil {
		return shim.Error("Failed to Unmarshal contractValue.json")
	}
	// Write the state to the ledger
      err = stub.PutState(ID, value)
     fmt.Printf("ID=%s,Value = %s\n",ID,string(value))
	if err != nil {
		return shim.Error(err.Error())
	}

	if transientMap, err := stub.GetTransient(); err == nil {
		if transientData, ok := transientMap["result"]; ok {
			return shim.Success(transientData)
		}
	}

	return shim.Success(nil)
	
}


func main() {
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}
